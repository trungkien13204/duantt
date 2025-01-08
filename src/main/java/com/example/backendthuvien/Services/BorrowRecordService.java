package com.example.backendthuvien.Services;

import com.example.backendthuvien.DTO.BorrowRecordDTO;
import com.example.backendthuvien.Repositories.BorrowRecordRepository;
import com.example.backendthuvien.Repositories.ProductRepository;
import com.example.backendthuvien.Repositories.UserRepository;
import com.example.backendthuvien.entity.BorrowRecord;
import com.example.backendthuvien.entity.Product;
import com.example.backendthuvien.entity.User;
import com.example.backendthuvien.exceptions.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BorrowRecordService {

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public BorrowRecord createBorrowRecord(BorrowRecordDTO borrowRecordDTO) throws Exception {
        // Kiểm tra sách
        Product product = productRepository.findById(borrowRecordDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy sách với ID: " + borrowRecordDTO.getProductId()));

        if (product.getQuantity() < 1) {
            throw new IllegalArgumentException("Sách không còn sẵn có trong kho.");
        }

        // Kiểm tra người mượn (khách hàng)
        User customer = userRepository.findById(borrowRecordDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy khách hàng với ID: " + borrowRecordDTO.getUserId()));

        if (!"USER".equals(customer.getRole().getName())) {
            throw new IllegalArgumentException("Người dùng không phải là khách hàng.");
        }

        // Kiểm tra nhân viên xử lý
        User staff = userRepository.findById(borrowRecordDTO.getStaffId())
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy nhân viên với ID: " + borrowRecordDTO.getStaffId()));

        if (!"ADMIN".equals(staff.getRole().getName())) {
            throw new IllegalArgumentException("Người dùng không phải là nhân viên.");
        }

        // Tạo bản ghi mượn sách
        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setUser(customer);
        borrowRecord.setProduct(product);
        borrowRecord.setBorrowDate(LocalDate.now());
        borrowRecord.setExpectedReturnDate(borrowRecordDTO.getExpectedReturnDate());
        borrowRecord.setStatus("not returned");
        borrowRecord.setStaff(staff);

        // Cập nhật số lượng sách
        product.setQuantity(product.getQuantity() - 1);
        productRepository.save(product);

        return borrowRecordRepository.save(borrowRecord);
    }

    public BorrowRecord returnBook(Long borrowRecordId, LocalDate actualReturnDate) throws Exception {
        BorrowRecord borrowRecord = borrowRecordRepository.findById(borrowRecordId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy bản ghi với ID: " + borrowRecordId));

        // Kiểm tra trạng thái
        if ("returned".equals(borrowRecord.getStatus())) {
            throw new IllegalStateException("Sách đã được trả.");
        }

        // Tính phí phạt nếu trả muộn
        if (actualReturnDate.isAfter(borrowRecord.getExpectedReturnDate())) {
            long daysLate = ChronoUnit.DAYS.between(borrowRecord.getExpectedReturnDate(), actualReturnDate);
            borrowRecord.setPenaltyFee((float) daysLate * 5000); // Ép kiểu về float

        }

        // Cập nhật trạng thái
        borrowRecord.setStatus("returned");
        borrowRecord.setActualReturnDate(actualReturnDate);

        // Cập nhật số lượng sách trong kho
        Product product = borrowRecord.getProduct();
        product.setQuantity(product.getQuantity() + 1);
        productRepository.save(product);

        return borrowRecordRepository.save(borrowRecord);
    }

    public List<BorrowRecord> getBorrowRecordsByUser(Long userId) {
        return borrowRecordRepository.findByUserId(userId);
    }


    public List<BorrowRecord> getOverdueBorrowRecords() {
        return borrowRecordRepository.findOverdueRecords();
    }
}

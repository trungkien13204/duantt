package com.example.backendthuvien.Repositories;

import com.example.backendthuvien.entity.BorrowRecord;
import com.example.backendthuvien.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord,Long> {
    /**
     * Lấy danh sách bản ghi mượn sách theo ID người dùng (CUSTOMER).
     */
    List<BorrowRecord> findByUserId(Long userId);

    /**
     * Lấy danh sách bản ghi mượn sách theo trạng thái.
     * @param status Trạng thái: 'not returned', 'returned', 'overdue'
     */
    List<BorrowRecord> findByStatus(String status);

    /**
     * Tìm tất cả các bản ghi sách quá hạn (status = 'overdue').
     */
    @Query("SELECT br FROM BorrowRecord br WHERE br.status = 'overdue'")
    List<BorrowRecord> findOverdueRecords();

    /**
     * Tìm các bản ghi mượn sách theo ID nhân viên xử lý (STAFF).
     */
    List<BorrowRecord> findByStaffId(Long staffId);

    /**
     * Tìm các bản ghi mượn sách theo ID sách (PRODUCT).
     */
    List<BorrowRecord> findByProductId(Long productId);

    /**
     * Tìm các bản ghi mượn sách theo ID người mượn và trạng thái.
     */
    List<BorrowRecord> findByUserIdAndStatus(Long userId, String status);
}

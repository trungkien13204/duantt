package com.example.backendthuvien.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRecordDTO {

    private Long userId; // ID người mượn sách (CUSTOMER)
    private Long productId; // ID sách mượn
    private LocalDate expectedReturnDate; // Ngày dự kiến trả sách
    private Long staffId; // ID nhân viên xử lý giao dịch (STAFF)

}

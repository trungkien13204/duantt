package com.example.backendthuvien.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "borrow_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Người mượn sách (phải là CUSTOMER)

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // Sách được mượn

    @Column(name = "borrow_date", nullable = false)
    private LocalDate borrowDate;

    @Column(name = "expected_return_date", nullable = false)
    private LocalDate expectedReturnDate;

    @Column(name = "actual_return_date")
    private LocalDate actualReturnDate;

    @Column(name = "status", nullable = false)
    private String status; // 'not returned', 'returned', 'overdue'

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private User staff; // Nhân viên xử lý giao dịch (phải là STAFF)

    @Column(name = "penalty_fee", columnDefinition = "FLOAT DEFAULT 0")
    private Float penaltyFee;

}

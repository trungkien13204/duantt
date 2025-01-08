package com.example.backendthuvien.Controller;

import com.example.backendthuvien.DTO.BorrowRecordDTO;
import com.example.backendthuvien.Services.BorrowRecordService;
import com.example.backendthuvien.entity.BorrowRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/borrow")
@RequiredArgsConstructor
public class BorrowRecordController {

    @Autowired
    private BorrowRecordService borrowRecordService;

    @PostMapping("")
    public ResponseEntity<BorrowRecord> borrowBook(@RequestBody BorrowRecordDTO borrowRecordDTO) {
        try {
            BorrowRecord borrowRecord = borrowRecordService.createBorrowRecord(borrowRecordDTO);
            return ResponseEntity.ok(borrowRecord);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @PutMapping("/return/{id}")
    public ResponseEntity<String> returnBook(
            @PathVariable Long id,
            @RequestParam("actualReturnDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate actualReturnDate) {
        try {
            BorrowRecord borrowRecord = borrowRecordService.returnBook(id, actualReturnDate);
            return ResponseEntity.ok("Sách đã được trả thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BorrowRecord>> getBorrowRecordsByUser(@PathVariable Long userId) {
        try {
            List<BorrowRecord> records = borrowRecordService.getBorrowRecordsByUser(userId);
            return ResponseEntity.ok(records);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @GetMapping("/overdue")
    public ResponseEntity<List<BorrowRecord>> getOverdueRecords() {
        try {
            List<BorrowRecord> overdueRecords = borrowRecordService.getOverdueBorrowRecords();
            return ResponseEntity.ok(overdueRecords);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}

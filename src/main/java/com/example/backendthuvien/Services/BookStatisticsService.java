package com.example.backendthuvien.Services;

import com.example.backendthuvien.Repositories.BorrowRecordRepository;
import com.example.backendthuvien.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookStatisticsService {
    @Autowired
    private ProductRepository bookRepository;
    public void updateBookStatistics() {
        Long totalBooks = bookRepository.countTotalBooks();
//        Long activeBooks = bookRepository.countActiveBooks();
        Long booksAddedToday = bookRepository.countBooksAddedToday();

        System.out.println("Total Books: " + totalBooks);
//        System.out.println("Active Books: " + activeBooks);
        System.out.println("Books Added Today: " + booksAddedToday);
    }
}

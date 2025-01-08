package com.example.backendthuvien.Services;

import com.example.backendthuvien.DTO.StatisticsDTO;
import com.example.backendthuvien.Repositories.Order_detailRepository;
import com.example.backendthuvien.Repositories.BorrowRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class StatisticsService {
    @Autowired
    private Order_detailRepository order_detailRepository;

    @Autowired
    private BorrowRecordRepository productReposutiry;
    public List<StatisticsDTO> getWeeklyStatistics() {
        List<Object[]> results = order_detailRepository.findWeeklyStatistics();
        return results.stream()
                .map(row -> new StatisticsDTO(
                        (String) row[0],          // productName
                        ((Number) row[1]).intValue(), // weekNumber
                        ((Number) row[2]).intValue(), // year
                        ((Number) row[3]).longValue() // totalQuantity
                ))
                .collect(Collectors.toList());
    }

    public List<StatisticsDTO> getMonthlyStatistics() {
        List<Object[]> results = order_detailRepository.findMonthlyStatistics();
        return results.stream()
                .map(row -> new StatisticsDTO(
                        (String) row[0],          // productName
                        ((Number) row[1]).intValue(), // month
                        ((Number) row[2]).intValue(), // year
                        ((Number) row[3]).longValue() // totalQuantity
                ))
                .collect(Collectors.toList());
    }
//    public Map<String, Long> getBookStatistics(LocalDate startDate, LocalDate endDate, String period) {
//        Map<String, Long> stats = new HashMap<>();
//
//        // Chuyển LocalDate thành LocalDateTime
//        LocalDateTime startDateTime = null;
//        LocalDateTime endDateTime = null;
//
//        if (startDate != null) {
//            startDateTime = startDate.atStartOfDay(); // Tạo thời gian bắt đầu trong ngày
//        }
//
//        if (endDate != null) {
//            endDateTime = endDate.atTime(23, 59, 59); // Tạo thời gian kết thúc (23:59:59)
//        }
//
//        // Nếu cả startDate và endDate đều có giá trị, tiến hành truy vấn thống kê
//        if (startDateTime != null && endDateTime != null) {
//            // Thống kê tổng số sản phẩm trong khoảng thời gian
//            Long totalBooks = productReposutiry.countTotalBooksByDateRange(startDateTime, endDateTime);
//            stats.put("totalBooks", totalBooks);
//
//            // Thống kê số sản phẩm còn hàng trong khoảng thời gian
//            Long availableProducts = productReposutiry.countAvailableProductsByDateRange(startDateTime, endDateTime);
//            stats.put("availableProducts", availableProducts);
//
//            // Thống kê số sản phẩm hết hàng trong khoảng thời gian
//            Long outOfStockProducts = productReposutiry.countOutOfStockProductsByDateRange(startDateTime, endDateTime);
//            stats.put("outOfStockProducts", outOfStockProducts);
//        }
//
//        // Nếu không có startDate hoặc endDate, thống kê theo period (daily, weekly, monthly...)
//        else if (period != null) {
//            switch (period.toLowerCase()) {
//                case "daily":
//                    LocalDateTime todayStart = LocalDate.now().atStartOfDay();
//                    LocalDateTime todayEnd = LocalDate.now().atTime(23, 59, 59);
//                    Long totalBooksDaily = productReposutiry.countTotalBooksByDateRange(todayStart, todayEnd);
//                    stats.put("totalBooks", totalBooksDaily);
//                    break;
//                case "weekly":
//                    LocalDateTime weekStart = LocalDate.now().minusWeeks(1).atStartOfDay();
//                    LocalDateTime weekEnd = LocalDate.now().atTime(23, 59, 59);
//                    Long totalBooksWeekly = productReposutiry.countTotalBooksByDateRange(weekStart, weekEnd);
//                    stats.put("totalBooks", totalBooksWeekly);
//                    break;
//                case "monthly":
//                    LocalDateTime monthStart = LocalDate.now().minusMonths(1).atStartOfDay();
//                    LocalDateTime monthEnd = LocalDate.now().atTime(23, 59, 59);
//                    Long totalBooksMonthly = productReposutiry.countTotalBooksByDateRange(monthStart, monthEnd);
//                    stats.put("totalBooks", totalBooksMonthly);
//                    break;
//                case "yearly":
//                    LocalDateTime yearStart = LocalDate.now().minusYears(1).atStartOfDay();
//                    LocalDateTime yearEnd = LocalDate.now().atTime(23, 59, 59);
//                    Long totalBooksYearly = productReposutiry.countTotalBooksByDateRange(yearStart, yearEnd);
//                    stats.put("totalBooks", totalBooksYearly);
//                    break;
//                default:
//                    // Thêm xử lý cho period không hợp lệ nếu cần
//                    break;
//            }
//        } else {
//            // Trường hợp không có startDate, endDate, hoặc period, lấy thống kê tổng quan
//            LocalDateTime nowStart = LocalDate.now().atStartOfDay();
//            LocalDateTime nowEnd = LocalDate.now().atTime(23, 59, 59);
//
//            Long totalBooks = productReposutiry.countTotalBooksByDateRange(nowStart, nowEnd);
//            stats.put("totalBooks", totalBooks);
//
//            Long availableProducts = productReposutiry.countAvailableProductsByDateRange(nowStart, nowEnd);
//            stats.put("availableProducts", availableProducts);
//
//            Long outOfStockProducts = productReposutiry.countOutOfStockProductsByDateRange(nowStart, nowEnd);
//            stats.put("outOfStockProducts", outOfStockProducts);
//        }
//
//        return stats;
//    }
}

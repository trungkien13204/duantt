    package com.example.backendthuvien.Controller;

    import com.example.backendthuvien.DTO.StatisticsDTO;
    import com.example.backendthuvien.Repositories.CategoriesRepositori;
    import com.example.backendthuvien.Repositories.BorrowRecordRepository;
    import com.example.backendthuvien.Repositories.UserRepository;
    import com.example.backendthuvien.Services.StatisticsService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;


    @RestController
    @RequestMapping("${api.prefix}/statistics")

    public class StatisticsController {
        @Autowired
        private StatisticsService statisticsService;
        @Autowired
        private BorrowRecordRepository productReposutiry;

        @Autowired
        private CategoriesRepositori categoriesRepositori;

        @Autowired
        private UserRepository userRepository;
        // API Thống kê theo tuần



        @GetMapping("/weekly")
        public ResponseEntity<List<StatisticsDTO>> getWeeklyStatistics() {
            try {
                List<StatisticsDTO> stats = statisticsService.getWeeklyStatistics();
                return ResponseEntity.ok(stats);
            } catch (Exception e) {
                return ResponseEntity.status(500).build();
            }
        }

        // API Thống kê theo tháng
        @GetMapping("/monthly")
        public ResponseEntity<List<StatisticsDTO>> getMonthlyStatistics() {
            try {
                List<StatisticsDTO> stats = statisticsService.getMonthlyStatistics();
                return ResponseEntity.ok(stats);
            } catch (Exception e) {
                return ResponseEntity.status(500).build();
            }
        }

//        @GetMapping("/books")
//        public Map<String, Long> getBookStatistics(
//                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
//                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
//                @RequestParam(required = false) String period) {
//
//            Map<String, Long> stats = new HashMap<>();
//
//            if (startDate != null && endDate != null) {
//                // Lọc theo khoảng thời gian cụ thể
//                Long totalBooks = productReposutiry.countTotalBooksByDateRange(startDate, endDate);
//                Long activeBooks = productReposutiry.countAvailableProductsByDateRange(startDate, endDate);
//                Long booksAddedToday = productReposutiry.countBooksAddedTodayByDateRange(startDate, endDate);
//
//                stats.put("totalBooks", totalBooks);
//                stats.put("quantityBooks", activeBooks);
//                stats.put("booksAddedToday", booksAddedToday);
//            } else if (period != null) {
//                // Lọc theo các khoảng thời gian định kỳ (ngày, tuần, tháng, năm)
//                switch (period.toLowerCase()) {
//                    case "daily":
//                        Long totalBooksDaily = productReposutiry.countTotalBooks();
//                        stats.put("totalBooks", totalBooksDaily);
//                        break;
//                    case "weekly":
//                        Long totalBooksWeekly = productReposutiry.countTotalBooksInWeek();
//                        stats.put("totalBooks", totalBooksWeekly);
//                        break;
//                    case "monthly":
//                        Long totalBooksMonthly = productReposutiry.countTotalBooksInMonth();
//                        stats.put("totalBooks", totalBooksMonthly);
//                        break;
//                    case "yearly":
//                        Long totalBooksYearly = productReposutiry.countTotalBooksInYear();
//                        stats.put("totalBooks", totalBooksYearly);
//                        break;
//                    default:
//                        break;
//                }
//            } else {
//                // Nếu không có các tham số, lấy thống kê tổng
//                Long totalBooks = productReposutiry.countTotalBooks();
//                Long activeBooks = productReposutiry.countAvailableProducts();
//                Long booksAddedToday = productReposutiry.countBooksAddedToday();
//
//                stats.put("totalBooks", totalBooks);
//                stats.put("quantityBooks", activeBooks);
//                stats.put("booksAddedToday", booksAddedToday);
//            }
//
//            return stats;
//        }
//

        // API để lấy thống kê thể loại
        @GetMapping("/categories")
        public Map<String, Long> getCategoryStatistics() {
            Map<String, Long> stats = new HashMap<>();

            // Lấy tổng số thể loại
            Long totalCategories = categoriesRepositori.countTotalCategories();

            // Lấy số thể loại thêm vào hôm nay


            // Đưa vào response
            stats.put("totalCategories", totalCategories);


            return stats;
        }

        // API để lấy thống kê người dùng

        @GetMapping("/users1")
        public Map<String, Long> getUserStatistics() {
            Map<String, Long> stats = new HashMap<>();

            // Lấy tổng số người dùng
            Long totalUsers = userRepository.countTotalUsers();

            // Lấy số người dùng active
            Long activeUsers = userRepository.countActiveUsers();

            // Lấy số người dùng thêm vào hôm nay
            Long usersAddedToday = userRepository.countUsersAddedToday();

            // Đưa vào response
            stats.put("totalUsers", totalUsers);
            stats.put("activeUsers", activeUsers);
            stats.put("usersAddedToday", usersAddedToday);

            return stats;
        }

//        @GetMapping("/books")
//        public ResponseEntity<Map<String, Long>> getProductStatistics(
//                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
//                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
//                @RequestParam(required = false) String period) {
//
//            // Gọi Service để lấy thống kê
//            Map<String, Long> stats = statisticsService.getBookStatistics(startDate, endDate, period);
//
//            // Trả về kết quả thống kê dưới dạng JSON
//            return ResponseEntity.ok(stats);
//        }



    }

package com.example.backendthuvien.Repositories;

import com.example.backendthuvien.entity.BorrowRecord;
import com.example.backendthuvien.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Boolean existsByName(String title);

    Page<Product> findAll(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE " +
            "(:categoryId IS NULL OR :categoryId = 0 OR p.categories.id = :categoryId) " +
            "AND (:keyword IS NULL OR :keyword = '' OR p.name LIKE %:keyword% OR p.description LIKE %:keyword%)")
    Page<Product> searchProducts(
            @Param("categoryId") Long categoryId,
            @Param("keyword") String keyword,
            Pageable pageable
    );
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.productImages WHERE p.id = :productId")
    Optional<Product> getDetailProduct(@Param("productId") Long productId);

    @Query("SELECT p FROM Product p WHERE p.id IN :productIds")
    List<Product> findProductsByIds(@Param("productIds") List<Long> productIds);
    List<Product> findByCatalogueContainingIgnoreCase(String catalogue);
    List<Product> findByCatalogue(String category);

    @Query(value = "SELECT COUNT(*) FROM Product WHERE quantity > 0", nativeQuery = true)
    Long countAvailableProducts();


    @Query(value = "SELECT COUNT(*) FROM Product WHERE DATE(created_at) = CURRENT_DATE", nativeQuery = true)
    Long countBooksAddedToday();

    @Query(value = "SELECT COUNT(*) FROM Product", nativeQuery = true)
    Long countTotalBooks();



//    @Query("SELECT COUNT(p) FROM Product p WHERE p.createdAt >= :startDate AND p.updatedAt <= :endDate")
//    Long countTotalBooksByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
//
//    // Còn hàng
//    @Query("SELECT COUNT(p) FROM Product p WHERE p.createdAt >= :startDate AND p.updatedAt <= :endDate AND p.quantity > 0")
//    Long countAvailableProductsByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
//
//    // Hết hàng
//    @Query("SELECT COUNT(p) FROM Product p WHERE p.createdAt >= :startDate AND p.updatedAt <= :endDate AND p.quantity = 0")
//    Long countOutOfStockProductsByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
//
//    // Tổng số sách trong khoảng thời gian
//    @Query("SELECT COUNT(p) FROM Product p WHERE p.createdAt >= :startDate AND p.updatedAt <= :endDate")
//    Long countTotalBooks(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);



//
//    // Thống kê số sách còn hoạt động (không có khoảng thời gian, tính tổng)
//
//
//    // Thống kê số sách trong tuần hiện tại
//    @Query("SELECT COUNT(p) FROM Product p WHERE p.createdAt >= CURRENT_DATE - 7")
//    Long countTotalBooksInWeek();
//
//    // Thống kê số sách trong tháng hiện tại
//    @Query("SELECT COUNT(p) FROM Product p WHERE EXTRACT(MONTH FROM p.createdAt) = EXTRACT(MONTH FROM CURRENT_DATE)")
//    Long countTotalBooksInMonth();
//
//    // Thống kê số sách trong năm hiện tại
//    @Query("SELECT COUNT(p) FROM Product p WHERE EXTRACT(YEAR FROM p.createdAt) = EXTRACT(YEAR FROM CURRENT_DATE)")
//    Long countTotalBooksInYear();
}

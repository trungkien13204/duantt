package com.example.backendthuvien.Controller;

import com.example.backendthuvien.DTO.OrderDTO;
import com.example.backendthuvien.DTO.categoryDTO;
import com.example.backendthuvien.Services.CouponService;
import com.example.backendthuvien.Services.OrderService;
import com.example.backendthuvien.entity.Coupon;
import com.example.backendthuvien.entity.DiscountType;
import com.example.backendthuvien.entity.Order;

import com.example.backendthuvien.reponse.BaseResponse;
import com.example.backendthuvien.reponse.OrderLisstResponse;
import com.example.backendthuvien.reponse.orderRespone;
import com.example.backendthuvien.util.LocalizationUtils;
import com.example.backendthuvien.util.MessageKey;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderCtrl {
    @Autowired
    private OrderService orderService;
    private final LocalizationUtils localizationUtils;
@Autowired private CouponService couponService;
    @GetMapping("/orders/search")
    public ResponseEntity<List<Order>> getOrdersByStatus(@RequestParam String status) {
        List<Order> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }
    @PostMapping("")

    public ResponseEntity<BaseResponse<Order>> createOrder(@Validated @RequestBody OrderDTO orderDTO, BindingResult result) {
        // Kiểm tra lỗi trong dữ liệu đầu vào
        if (result.hasErrors()) {
            // Trả về danh sách các lỗi đầu vào
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                    BaseResponse.<Order>builder()
                            .code("400")
                            .message("Dữ liệu không hợp lệ")
                            .data(null)
                            .build()
            );
        }

        try {
            // Gọi service để tạo đơn hàng
            Order newOrder = orderService.creatOrder(orderDTO);

            // Trả về đơn hàng đã tạo thành công
            return ResponseEntity.ok( BaseResponse.<Order>builder()
                    .code("201")
                    .message("Tạo đơn hàng thành công")
                    .data(newOrder)
                    .build());

        } catch (IllegalArgumentException ex) {
            // Xử lý các lỗi do logic không hợp lệ
            return ResponseEntity.badRequest().body( BaseResponse.<Order>builder()
                    .code("400")
                    .message(ex.getMessage())
                    .data(null)
                    .build());
        } catch (Exception ex) {
            // Xử lý các lỗi hệ thống hoặc không mong đợi
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( BaseResponse.<Order>builder()
                    .code("500")
                    .message("Đã xảy ra lỗi: " + ex.getMessage())
                    .data(null)
                    .build());
        }
    }



    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getOders(@Validated @PathVariable("user_id") long userID) {
        try {
            List<Order> orders = orderService.findByUsserId(userID);

            return ResponseEntity.ok( BaseResponse.<List<Order>>builder()
                    .code("200")
                    .message("Lấy đơn hàng thành công")
                    .data(orders)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body( BaseResponse.<Order>builder()
                    .code("500")
                    .message("Đã xảy ra lỗi: " + e.getMessage())
                    .data(null)
                    .build());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOder(@Validated @PathVariable("id") long orderId) {
        try {
            Order exiting = orderService.getOrder(orderId);
            orderRespone a=orderRespone.fromOrder(exiting);
            return ResponseEntity.ok( BaseResponse.<orderRespone>builder()
                    .code("200")
                    .message("Lấy chi tiết đơn hàng thành công")
                    .data(a)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body( BaseResponse.<orderRespone>builder()
                    .code("400")
                    .message("Lỗi khi lấy chi tiết đơn hàng: " + e.getMessage())
                    .data(null)
                    .build()
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Order>> update(@Validated @PathVariable long id, @RequestBody OrderDTO orderDTO) {
        try {

            Order order = orderService.updateOrder(id, orderDTO);
            return ResponseEntity.ok( BaseResponse.<Order>builder()
                    .code("200")
                    .message("Cập nhật đơn hàng thành công")
                    .data(order)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body( BaseResponse.<Order>builder()
                    .code("400")
                    .message("Lỗi khi cập nhật đơn hàng: " + e.getMessage())
                    .data(null)
                    .build());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
       try{
           orderService.deleteOrder(id);
           Map<String, String> response = new HashMap<>();
           response.put("message", "Xóa thành công");
           return ResponseEntity.ok( BaseResponse.<Map<String, String>>builder()
                   .code("200")
                   .message("Xóa đơn hàng thành công")
                   .data(Map.of("id", id.toString()))
                   .build()); // Trả về JSON thay vì plain text
       }catch (Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                   BaseResponse.<Map<String, String>>builder()
                           .code("500")
                           .message("Lỗi khi xóa đơn hàng: " + e.getMessage())
                           .data(null)
                           .build()
           );

       }
    }

    @GetMapping("/get-orders-by-keyword")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse<OrderLisstResponse>> getOrdersByKeyword(
            @RequestParam(defaultValue = "", required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {

        // Tạo Pageable từ thông tin trang và giới hạn
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                // Sort.by("createdAt").descending()
                Sort.by("id").ascending()
        );

        Page<orderRespone> orderPage = orderService
                .getOrdersByKeyword(keyword, pageRequest)
                .map(orderRespone::fromOrder);

        // Lấy tổng số trang
        int totalPages = orderPage.getTotalPages();
        OrderLisstResponse response = OrderLisstResponse.builder()
                .orders(orderPage.getContent())
                .totalPages(orderPage.getTotalPages())
                .build();

        return ResponseEntity.ok(BaseResponse.<OrderLisstResponse>builder()
                .code("200")
                .message("Tìm kiếm đơn hàng thành công")
                .data(response)
                .build());
    }


    private float calculateDiscount(OrderDTO orderDTO, Coupon coupon) {
        float discountAmount = 0;

        // Kiểm tra giá trị đơn hàng và loại giảm giá
        if (coupon != null && orderDTO.getTotalMoney() >= coupon.getMinOrderValue()) {
            if (coupon.getDiscountType() == DiscountType.PERCENTAGE) {
                // Giảm giá theo phần trăm
                discountAmount = (coupon.getDiscountValue() / 100) * orderDTO.getTotalMoney();
            } else if (coupon.getDiscountType() == DiscountType.FIXED) {
                // Giảm giá cố định
                discountAmount = coupon.getDiscountValue();
            }

            // Kiểm tra giới hạn giảm giá tối đa
            if (coupon.getMaxDiscountValue() != null && discountAmount > coupon.getMaxDiscountValue()) {
                discountAmount = coupon.getMaxDiscountValue();
            }
        }

        return discountAmount;
    }

//    @PostMapping("/borrow")
//    public ResponseEntity<Order> createBorrowOrder(@RequestBody OrderDTO orderDTO) throws Exception {
//        Order order = orderService.createBorrowOrder(orderDTO);
//        return ResponseEntity.ok(order);
//    }
//
//    @PutMapping("/return/{orderDetailId}")
//    public ResponseEntity<String> returnBook(
//            @PathVariable Long orderDetailId,
//            @RequestParam("actualReturnDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate actualReturnDate) throws Exception {
//        orderService.returnBook(orderDetailId, actualReturnDate);
//        return ResponseEntity.ok("Trả sách thành công!");
//    }

}

package com.example.backendthuvien.Controller;

import com.example.backendthuvien.DTO.OrderDTO;
import com.example.backendthuvien.DTO.categoryDTO;
import com.example.backendthuvien.Services.CategorisService;
import com.example.backendthuvien.entity.Categories;
import com.example.backendthuvien.exceptions.CustomException;
import com.example.backendthuvien.reponse.BaseResponse;
import com.example.backendthuvien.reponse.LoginRespone;
import com.example.backendthuvien.reponse.UpdateCategoryReponses;
import com.example.backendthuvien.util.LocalizationUtils;
import com.example.backendthuvien.util.MessageKey;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class categorisCtrl {

    private final LocalizationUtils localizationUtils;
    @Autowired
    private CategorisService categorisService;

    @PostMapping("")
    public ResponseEntity<BaseResponse<categoryDTO>> add(@Validated @RequestBody categoryDTO c) {
        categorisService.createCategory(c);
        return ResponseEntity.ok(
                BaseResponse.<categoryDTO>builder()
                        .code("200")
                        .message("Thêm danh mục thành công")
                        .data(c)
                        .build()
        );
    }
//    @PostMapping("")
//    public ResponseEntity<BaseResponse<categoryDTO>> add(@Validated @RequestBody categoryDTO c, BindingResult result) {
//        if (result.hasErrors()) {
//            List<String> errors = result.getFieldErrors().stream()
//                    .map(FieldError::getDefaultMessage)
//                    .toList();
//            // Trả về lỗi dưới dạng JSON
//            return ResponseEntity.badRequest().body(
//                    BaseResponse.<categoryDTO>builder()
//                            .code("400")
//                            .message("Validation failed")
//                            .data(c)
//                    .build()
//            );
//        }
//
//        // Tạo danh mục mới
//        try {
//            categorisService.createCategory(c);
//            // Trả về phản hồi JSON khi thêm thành công
//            return ResponseEntity.status(HttpStatus.CREATED).body(
//                    BaseResponse.<categoryDTO>builder()
//                    .code("200")
//                    .message("Thêm thành công")
//                    .data(c)
//                    .build()
//            );
//        } catch (Exception e) {
//            // Xử lý lỗi trong trường hợp có vấn đề khác
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseResponse.<categoryDTO>builder()
//                    .code("500")
//                    .message("Thêm thất bại"+e.getMessage())
//                    .data(null)
//                    .build());
//        }
//    }
//

    @GetMapping("")
    public ResponseEntity<BaseResponse<List<Categories>>> index() {
        List<Categories> cate = categorisService.getAllCategories();
        return ResponseEntity.ok(
                BaseResponse.<List<Categories>>builder()
                        .code("200")
                        .message("Lấy danh sách danh mục thành công")
                        .data(cate)
                        .build()
        );
    }

    @PutMapping("/{id}")

    public ResponseEntity<BaseResponse<Map<String, Object>>> update(
            @PathVariable long id,
            @RequestBody categoryDTO categoryDTO) {

        // Gọi Service để cập nhật danh mục
        Categories updatedCategory = categorisService.updateCategories(id, categoryDTO);

        // Tạo dữ liệu trả về sau khi cập nhật
        Map<String, Object> responseData = Map.of(
                "id", updatedCategory.getId(),
                "name", updatedCategory.getName()
        );

        // Trả về response chuẩn
        return ResponseEntity.ok(
                BaseResponse.<Map<String, Object>>builder()
                        .code("200")
                        .message("Cập nhật danh mục thành công")
                        .data(responseData)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Long>> delete(@PathVariable long id) {
        categorisService.deleteCategories(id);
        return ResponseEntity.ok(
                BaseResponse.<Long>builder()
                        .code("200")
                        .message("Xoá danh mục thành công")
                        .data(id)
                        .build()
        );
    }


    @GetMapping("/search")
    public ResponseEntity<BaseResponse<List<Categories>>> searchByName(@RequestParam String name) {
        // Làm sạch giá trị name
        String sanitizedInput = name.trim().replaceAll("^,+|,+$", ""); // Loại bỏ dấu phẩy đầu/cuối và khoảng trắng


        // Gọi Service để tìm kiếm danh mục
        List<Categories> categories = categorisService.searchCategoriesByName(sanitizedInput);

        // Trả về kết quả tìm kiếm thành công
        return ResponseEntity.ok(
                BaseResponse.<List<Categories>>builder()
                        .code("200")
                        .message("Tìm kiếm danh mục thành công")
                        .data(categories)
                        .build()
        );
    }


}

package com.example.backendthuvien.exceptions;

import com.example.backendthuvien.reponse.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Xử lý CustomException 400
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse<Void>> handleCustomException(CustomException ex) {
        return ResponseEntity.badRequest().body(
                BaseResponse.<Void>builder()
                        .code(ex.getCode())
                        .message(ex.getMessage())
                        .data(null)
                        .build()
        );
    }

    // Xử lý Validation Errors 422
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Map<String, String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.unprocessableEntity().body(
                BaseResponse.<Map<String, String>>builder()
                        .code("422")
                        .message("Dữ liệu không hợp lệ")
                        .data(errors)
                        .build()
        );
    }

    // Xử lý Exception chung 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Void>> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                BaseResponse.<Void>builder()
                        .code("500")
                        .message("Lỗi hệ thống: " + ex.getMessage())
                        .data(null)
                        .build()
        );
    }
}

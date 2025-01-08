package com.example.backendthuvien.reponse;

import com.example.backendthuvien.entity.Comment;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponse {
    private Long id;
    private Long userId;
    private String userName;
    private Long productId;
    private String content;
    private LocalDateTime createdAt;

    // Phương thức chuyển đổi từ entity Comment sang CommentResponse

}

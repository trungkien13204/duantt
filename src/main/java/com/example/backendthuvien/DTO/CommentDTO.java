package com.example.backendthuvien.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Integer id;
    private Integer postId;
    private Integer userId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

package com.example.backendthuvien.DTO;

import com.example.backendthuvien.entity.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDTO {
    private Integer id;
    private String name;
    private String description;
    private String author;
    private Integer totalLike;
    private Long productId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.name = post.getName();
        this.description = post.getDescription();
        this.author = post.getAuthor();
        this.totalLike = post.getTotalLike();
        this.productId = post.getProductt().getId();
    }
}

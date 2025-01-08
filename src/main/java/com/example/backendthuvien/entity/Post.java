package com.example.backendthuvien.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "post")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    private String author;

    @Column(name = "totalLike")
    private Integer totalLike = 0;

    @Column(name = "created_at",nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
@Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productID", nullable = false)
    private Product productt;
}


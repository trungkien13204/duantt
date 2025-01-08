package com.example.backendthuvien.Repositories;

import com.example.backendthuvien.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Long> {
    @Query("SELECT p FROM Post p WHERE p.productt.id = :productId")
    List<Post> findByProductId(Long productId);
}

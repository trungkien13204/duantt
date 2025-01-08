package com.example.backendthuvien.Controller;

import com.example.backendthuvien.DTO.PostDTO;
import com.example.backendthuvien.Services.PostService;
import com.example.backendthuvien.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/post")
@RequiredArgsConstructor
public class PostCtrl {
    @Autowired
    private PostService postService;
    @GetMapping
    public ResponseEntity<List<PostDTO>> getPostsByProductId(@RequestParam Long productId) {
        try {
            List<PostDTO> posts = postService.getPostsByProductId(productId);
            if (posts.isEmpty()) {
                return ResponseEntity.noContent().build(); // Trả về 204 nếu không có bài viết
            }
            return ResponseEntity.ok(posts); // Trả về danh sách bài viết
        } catch (Exception e) {
            // Log lỗi
            e.printStackTrace();
            return ResponseEntity.internalServerError().build(); // Trả về 500 nếu có lỗi
        }
    }

    @PostMapping
    public Post createPost(@RequestBody Post post, @RequestParam Long productId) {
        return postService.createPost(post, productId);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post postDetails) {
        return postService.updatePost(id, postDetails);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }
}

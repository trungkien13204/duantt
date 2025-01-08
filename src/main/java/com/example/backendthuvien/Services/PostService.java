package com.example.backendthuvien.Services;

import com.example.backendthuvien.DTO.PostDTO;
import com.example.backendthuvien.Repositories.PostRepo;
import com.example.backendthuvien.Repositories.BorrowRecordRepository;
import com.example.backendthuvien.Repositories.ProductRepository;
import com.example.backendthuvien.entity.Post;
import com.example.backendthuvien.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostRepo postRepository;
    @Autowired
    private ProductRepository productReposutiry;

    public List<PostDTO> getPostsByProductId(Long productId) {
        List<Post> posts = postRepository.findByProductId(productId);
        return posts.stream().map(PostDTO::new).collect(Collectors.toList());
    }
    public Post createPost(Post post, Long productId) {
        Product product = productReposutiry.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        post.setProductt(product);
        return postRepository.save(post);
    }

    public Post updatePost(Long id, Post postDetails) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        post.setName(postDetails.getName());
        post.setDescription(postDetails.getDescription());
        post.setAuthor(postDetails.getAuthor());
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}

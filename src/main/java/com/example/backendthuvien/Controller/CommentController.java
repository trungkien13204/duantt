package com.example.backendthuvien.Controller;

import com.example.backendthuvien.Services.CommentService;
import com.example.backendthuvien.Services.PostService;
import com.example.backendthuvien.entity.Comment;
import com.example.backendthuvien.reponse.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/comment")
@RequiredArgsConstructor
public class CommentController {

    @Autowired
    private CommentService commentService;

//    @GetMapping("/post/{postId}")
//    public List<Comment> getCommentsByPostId(@PathVariable Integer postId) {
//        return commentService.getAllCommentsByPostId(postId);
//    }

//    @PostMapping
//    public Comment createComment(@RequestBody Comment comment) {
//        return commentService.createComment(comment);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteComment(@PathVariable Long id) {
//        commentService.deleteComment(id);
//    }
}

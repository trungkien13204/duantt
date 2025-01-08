package com.example.backendthuvien.Services;

import org.springframework.stereotype.Service;

@Service
public class CommentService {
//
//    @Autowired
//    private CommentRepo commentRepository;
//    @Autowired
//    private PostRepo postRepository;
//    public List<CommentDTO> getCommentsByPostId(Long postId) {
//        return commentRepository.findByPostId(postId).stream()
//                .map(CommentDTO::new)
//                .collect(Collectors.toList());
//    }
//
//    public CommentDTO createComment(CommentDTO commentDTO) {
//        Comment comment = new Comment();
//        comment.setContent(commentDTO.getContent());
//        comment.setUserId(commentDTO.getUserId());
//        comment.setPost(postRepository.findById(commentDTO.getPostId())
//                .orElseThrow(() -> new RuntimeException("Post not found")));
//        return new CommentDTO(commentRepository.save(comment));
//    }
//
//    public CommentDTO updateComment(Long id, CommentDTO commentDTO) {
//        Comment comment = commentRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Comment not found"));
//        comment.setContent(commentDTO.getContent());
//        return new CommentDTO(commentRepository.save(comment));
//    }
//
//    public void deleteComment(Long id) {
//        if (!commentRepository.existsById(id)) {
//            throw new RuntimeException("Comment not found");
//        }
//        commentRepository.deleteById(id);
//    }
}

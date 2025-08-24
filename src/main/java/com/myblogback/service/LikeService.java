package com.myblogback.service;

import com.myblogback.domain.entity.*;
import com.myblogback.dto.LikeResponse;
import com.myblogback.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final LikePostRepository likePostRepository;
    private final LikeCommentRepository likeCommentRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    //게시글 좋아요
    public LikeResponse getPostLike(Long postId, Long userId) {
        boolean liked = (userId != null) && likePostRepository.existsByUserIdAndPostId(userId, postId);
        long count = likePostRepository.countByPostId(postId);
        return new LikeResponse(liked, count);
    }

    @Transactional
    public Optional<LikeResponse> togglePostLike(Long postId, Long userId) {
        if (userId == null) return Optional.empty();
        Optional<Post> post = postRepository.findById(postId);
        Optional<User> user = userRepository.findById(userId);
        if (post.isEmpty() || user.isEmpty()) return Optional.empty();

        boolean exists = likePostRepository.existsByUserIdAndPostId(userId, postId);
        if (exists) {
            likePostRepository.deleteByUserIdAndPostId(userId, postId); // unlike
        } else {
            LikePost lp = LikePost.builder().post(post.get()).user(user.get()).build();
            likePostRepository.save(lp); // like
        }
        boolean liked = !exists;
        long count = likePostRepository.countByPostId(postId);
        return Optional.of(new LikeResponse(liked, count));
    }

    //댓글 좋아요
    public LikeResponse getCommentLike(Long commentId, Long userId) {
        boolean liked = (userId != null) && likeCommentRepository.existsByUserIdAndCommentId(userId, commentId);
        long count = likeCommentRepository.countByCommentId(commentId);
        return new LikeResponse(liked, count);
    }

    @Transactional
    public Optional<LikeResponse> toggleCommentLike(Long commentId, Long userId) {
        if (userId == null) return Optional.empty();
        Optional<Comment> comment = commentRepository.findById(commentId);
        Optional<User> user = userRepository.findById(userId);
        if (comment.isEmpty() || user.isEmpty()) return Optional.empty();

        boolean exists = likeCommentRepository.existsByUserIdAndCommentId(userId, commentId);
        if (exists) {
            likeCommentRepository.deleteByUserIdAndCommentId(userId, commentId); // unlike
        } else {
            LikeComment lc = LikeComment.builder().comment(comment.get()).user(user.get()).build();
            likeCommentRepository.save(lc); // like
        }
        boolean liked = !exists;
        long count = likeCommentRepository.countByCommentId(commentId);
        return Optional.of(new LikeResponse(liked, count));
    }
}

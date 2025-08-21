package com.myblogback.service;

import com.myblogback.domain.entity.Post;
import com.myblogback.dto.*;
import com.myblogback.repository.PostRepository;
import com.myblogback.domain.entity.User;
import com.myblogback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    @Transactional
    public PostResponse create(Long authorId, PostCreateRequest request) {
        User author = userService.getById(authorId);
        if (author == null) throw new IllegalArgumentException("작성자 정보를 찾을 수 없음");
        Post post = Post.builder()
                .title(request.title())
                .content(request.content())
                .author(author)
                .build();
                //.thumbnailUrl(request.thumbnailUrl())
        return PostResponse.from(postRepository.save(post));
    } // 게시글 작성

    @Transactional(readOnly = true)
    public Page<PostResponse> list(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostResponse::from);
    } // 게시글 목록

    @Transactional(readOnly = true)
    public PostResponse get(Long id) {
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) return null;
        return PostResponse.from(post);
    } // 게시글 조회

    @Transactional
    public PostResponse update(Long userId, Long postId, PostUpdateRequest request) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) throw new IllegalArgumentException("게시글 없는데용");
        if (!post.getAuthor().getId().equals(userId)) {
            throw new IllegalArgumentException("작성자가 아니신데여");
        }
        post.setTitle(request.title());
        post.setContent(request.content());
        // post.setThumbnailUrl(request.thumbnailUrl()); // 썸네일 넣을거면
        return PostResponse.from(postRepository.save(post));
    } // 게시글 수정

    @Transactional
    public void delete(Long userId, Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) throw new IllegalArgumentException("게시글 없는데용");
        if (!post.getAuthor().getId().equals(userId)) {
            throw new IllegalArgumentException("작성자가 아니신데여");
        }
        postRepository.delete(post);
    }
}

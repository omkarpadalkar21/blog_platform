package com.omkar.blog.controllers;

import com.omkar.blog.domain.CreatePostRequest;
import com.omkar.blog.domain.UpdatePostRequest;
import com.omkar.blog.domain.dtos.CreatePostRequestDto;
import com.omkar.blog.domain.dtos.PostDto;
import com.omkar.blog.domain.dtos.UpdatePostRequestDto;
import com.omkar.blog.domain.entities.Post;
import com.omkar.blog.domain.entities.User;
import com.omkar.blog.mappers.PostMapper;
import com.omkar.blog.services.PostService;
import com.omkar.blog.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam(required = false) UUID categoryId,
            @RequestBody(required = false) UUID tagId
    ) {
        List<Post> posts = postService.getAllPosts(categoryId, tagId);
        List<PostDto> postDtos = posts.stream().map(postMapper::toDto).toList();

        return ResponseEntity.ok(postDtos);
    }

    @GetMapping(path = "/drafts")
    public ResponseEntity<List<PostDto>> getDrafts(@RequestAttribute UUID userId) {
        User user = userService.getUserById(userId);
        List<Post> draftPosts = postService.getDraftPosts(user);
        List<PostDto> postDtos = draftPosts.stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(postDtos);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(
            @Valid @RequestBody CreatePostRequestDto createPostRequestDto,
            @RequestBody UUID userId
    ) {
        User user = userService.getUserById(userId);
        CreatePostRequest createPostRequest = postMapper.toCreatePostRequest(createPostRequestDto);
        Post createdPost = postService.createPost(user, createPostRequest);
        PostDto createdPostDto = postMapper.toDto(createdPost);

        return new ResponseEntity<>(
                createdPostDto,
                HttpStatus.CREATED
        );
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PostDto> updatePost(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePostRequestDto updatePostRequestDto
    ) {
        UpdatePostRequest updatePostRequest = postMapper.toUpdatePostRequest(updatePostRequestDto);
        Post updatedPost = postService.updatePost(id, updatePostRequest);
        PostDto updatedPostDto = postMapper.toDto(updatedPost);
        return ResponseEntity.ok(updatedPostDto);
    }
}

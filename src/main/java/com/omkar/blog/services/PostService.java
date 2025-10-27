package com.omkar.blog.services;

import com.omkar.blog.domain.CreatePostRequest;
import com.omkar.blog.domain.UpdatePostRequest;
import com.omkar.blog.domain.entities.Post;
import com.omkar.blog.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<Post> getAllPosts(UUID categoryId, UUID postId);

    List<Post> getDraftPosts(User user);

    Post createPost(User user, CreatePostRequest createPostRequest);

    Post updatePost(UUID id, UpdatePostRequest updatePostRequest);

    Post getPost(UUID id);
}

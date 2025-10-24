package com.omkar.blog.services;

import com.omkar.blog.domain.entities.Post;
import com.omkar.blog.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<Post> getAllPosts(UUID categoryId, UUID postId);

    List<Post> getDraftPosts(User user);
}

package com.omkar.blog.repositories;

import com.omkar.blog.domain.PostStatus;
import com.omkar.blog.domain.entities.Category;
import com.omkar.blog.domain.entities.Post;
import com.omkar.blog.domain.entities.Tag;
import com.omkar.blog.services.PostService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findAllByPostStatusAndCategoryAndTagsContaining(PostStatus postStatus, Category category, Tag tag);

    List<Post> findAllByPostStatusAndCategory(PostStatus postStatus, Category category);

    List<Post> findAllByPostStatusAndTagsContaining(PostStatus postStatus, Tag tag);

    List<Post> findAllByPostStatus(PostStatus status);
}

package com.omkar.blog.repositories;

import com.omkar.blog.domain.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {
    @Query("select t from Tag t left join fetch t.posts")
    List<Tag> findAllByPostCount();
}

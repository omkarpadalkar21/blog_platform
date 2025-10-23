package com.omkar.blog.services;

import com.omkar.blog.domain.entities.Tag;

import java.util.List;
import java.util.Set;

public interface TagService {
    List<Tag> getTags();

    List<Tag> createTags(Set<String> tagNames);
}

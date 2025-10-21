package com.omkar.blog.services;

import com.omkar.blog.domain.entities.Tag;

import java.util.List;

public interface TagService {
    List<Tag> getTags();
}

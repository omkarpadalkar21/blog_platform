package com.omkar.blog.services.impl;

import com.omkar.blog.domain.entities.Tag;
import com.omkar.blog.repositories.TagRepository;
import com.omkar.blog.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public List<Tag> getTags() {
        return tagRepository.findAllByPostCount();
    }
}

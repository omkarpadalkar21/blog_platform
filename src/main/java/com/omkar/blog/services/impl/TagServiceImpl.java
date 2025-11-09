package com.omkar.blog.services.impl;

import com.omkar.blog.domain.entities.Tag;
import com.omkar.blog.repositories.TagRepository;
import com.omkar.blog.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    @Cacheable(cacheNames = "tags")
    public List<Tag> getTags() {
        return tagRepository.findAllByPostCount();
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "tags",allEntries = true)
    public List<Tag> createTags(Set<String> tagNames) {
        List<Tag> existingTags = tagRepository.findByNameIn(tagNames);
        Set<String> existingTagNames = existingTags.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());

        List<Tag> newTags = tagNames.stream()
                .filter(name -> !existingTagNames.contains(name))
                .map(name -> Tag.builder()
                        .name(name)
                        .posts(new HashSet<>())
                        .build()).toList();

        List<Tag> savedTags = new ArrayList<>();
        if (!newTags.isEmpty()) {
            savedTags = tagRepository.saveAll(newTags);
        }
        savedTags.addAll(existingTags);
        return savedTags;
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "tag", key = "#id"),
            @CacheEvict(cacheNames = "tags", allEntries = true)
    })
    public void deleteTag(UUID id) {
        tagRepository.findById(id).ifPresent(
                tag -> {
                    if (!tag.getPosts().isEmpty()) {
                        throw new IllegalStateException("Can't delete tags with posts");
                    }
                    tagRepository.deleteById(id);
                }
        );
    }

    @Override
    @Cacheable(cacheNames = "tag",key = "#id")
    public Tag getTagById(UUID id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found with id: " + id));
    }

    @Override
    public List<Tag> getTagByIds(Set<UUID> ids) {
        List<Tag> foundTags = tagRepository.findAllById(ids);

        if (foundTags.size() != ids.size()) {
            throw new EntityNotFoundException("All tags specified not found!");
        }

        return foundTags;
    }
}

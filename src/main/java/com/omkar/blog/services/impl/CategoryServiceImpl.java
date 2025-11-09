package com.omkar.blog.services.impl;

import com.omkar.blog.domain.entities.Category;
import com.omkar.blog.repositories.CategoryRepository;
import com.omkar.blog.services.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Cacheable(cacheNames = "categories")
    public List<Category> listCategory() {
        return categoryRepository.findAllWithPostCount();
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = {"categories", "category"}, allEntries = true)
    public Category createCategory(Category category) {
        if (categoryRepository.existsByNameIgnoreCase(category.getName())) {
            throw new IllegalArgumentException("Category already exists with name: " + category.getName());
        }

        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(UUID id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            if (!category.get().getPosts().isEmpty()) {
                throw new IllegalStateException("Category has posts associated with it!");
            }
            categoryRepository.deleteById(id);
        }
    }

    @Override
    @Cacheable(cacheNames = "category", key = "#id")
    public Category getCategoryById(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
    }
}

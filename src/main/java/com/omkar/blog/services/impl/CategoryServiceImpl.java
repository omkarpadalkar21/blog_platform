package com.omkar.blog.services.impl;

import com.omkar.blog.domain.entities.Category;
import com.omkar.blog.repositories.CategoryRepository;
import com.omkar.blog.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> listCategory() {
        return categoryRepository.findAllWithPostCount();
    }
}

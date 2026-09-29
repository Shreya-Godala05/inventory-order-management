package com.example.inventory.service;

import com.example.inventory.model.Category;
import com.example.inventory.repository.CategoryRepository;

import java.util.List;

public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService() {
        this.categoryRepository = new CategoryRepository();
    }

    public List<Category> getAllCategories() {
        return categoryRepository.getAllCategories();
    }
}
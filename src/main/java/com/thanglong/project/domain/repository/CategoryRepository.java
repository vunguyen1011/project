package com.thanglong.project.domain.repository;

import com.thanglong.project.domain.model.CategoryModel;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    boolean existsByName(String name);

    CategoryModel save(CategoryModel categoryModel);

    Optional<CategoryModel> findById(Long categoryId);

    List<CategoryModel> findAll();

    boolean existsById(Long categoryId);

    void deleteById(Long categoryId);
}

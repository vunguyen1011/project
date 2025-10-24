package com.thanglong.project.domain.repository;

import com.thanglong.project.domain.model.CategoryModel;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    boolean existsByName(String name);

    CategoryModel save(CategoryModel categoryModel);

    Optional<CategoryModel> findById(Integer categoryId);

    List<CategoryModel> findAll();

    boolean existsById(Integer categoryId);

    void deleteById(Integer categoryId);
}

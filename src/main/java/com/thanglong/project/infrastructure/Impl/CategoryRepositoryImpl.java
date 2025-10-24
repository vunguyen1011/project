package com.thanglong.project.infrastructure.Impl;

import com.thanglong.project.domain.model.CategoryModel;
import com.thanglong.project.domain.repository.CategoryRepository;
import com.thanglong.project.infrastructure.Entity.Category;
import com.thanglong.project.infrastructure.Mapper.CategoryMapper;
import com.thanglong.project.infrastructure.repository.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository // Đánh dấu đây là Spring component
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {
    private final CategoryJpaRepository categoryJpaRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public boolean existsByName(String name) {
        // Gọi thẳng phương thức của JpaRepository
        return categoryJpaRepository.existsByName(name);
    }

    @Override
    public CategoryModel save(CategoryModel categoryModel) {
        // 1. Dùng Mapper chuyển Model (domain) sang Entity (infra)
        Category categoryEntity = categoryMapper.toEntity(categoryModel);

        // 2. Dùng JpaRepository để lưu Entity
        Category savedEntity = categoryJpaRepository.save(categoryEntity);

        // 3. Dùng Mapper chuyển Entity đã lưu (có ID) về Model để trả ra
        return categoryMapper.toModel(savedEntity);
    }

    @Override
    public Optional<CategoryModel> findById(Long categoryId) {
        // 1. Dùng JpaRepository tìm Entity
        Optional<Category> entityOptional = categoryJpaRepository.findById(categoryId);

        // 2. Dùng Mapper để map Optional<Entity> sang Optional<Model>
        return entityOptional.map(categoryMapper::toModel);
    }

    @Override
    public List<CategoryModel> findAll() {
        // 1. Dùng JpaRepository lấy tất cả Entities
        List<Category> entities = categoryJpaRepository.findAll();

        // 2. Dùng Mapper để chuyển List<Entity> sang List<Model>
        return entities.stream()
                .map(categoryMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long categoryId) {
        // Gọi thẳng phương thức của JpaRepository
        return categoryJpaRepository.existsById(categoryId);
    }

    @Override
    public void deleteById(Long categoryId) {
        // Gọi thẳng phương thức của JpaRepository
        categoryJpaRepository.deleteById(categoryId);
    }
}

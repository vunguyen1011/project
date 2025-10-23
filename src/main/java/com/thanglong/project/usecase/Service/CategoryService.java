package com.thanglong.project.usecase.Service;

import com.thanglong.project.domain.ENUM.ErrorCode;
import com.thanglong.project.domain.model.CategoryModel;
import com.thanglong.project.domain.repository.CategoryRepository;
import com.thanglong.project.usecase.Exception.WebErrorConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    // == CREATE ==
    public CategoryModel createCategory(String name) {
        if (categoryRepository.existsByName(name))
            throw new WebErrorConfig(ErrorCode.CATEGORY_ALREADY_EXITED); // <-- Cần ErrorCode này

        CategoryModel categoryModel = CategoryModel.builder()
                .name(name)
                .build();
        return categoryRepository.save(categoryModel);
    }

    // == READ (Get one by ID) ==
    public CategoryModel getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.CATEGORY_NOT_FOUND)); // <-- Cần ErrorCode này
    }

    // == READ (Get all) ==
    public List<CategoryModel> getAllCategories() {
        return categoryRepository.findAll();
    }

    // == UPDATE ==
    public CategoryModel updateCategory(Long categoryId, String newName) {
        // 1. Tìm category hiện có
        CategoryModel existingCategory = getCategoryById(categoryId); // Tái sử dụng hàm getById

        // 2. Kiểm tra xem tên mới đã tồn tại ở category khác chưa
        if (!existingCategory.getName().equals(newName) && categoryRepository.existsByName(newName)) {
            throw new WebErrorConfig(ErrorCode.CATEGORY_ALREADY_EXITED);
        }

        // 3. Cập nhật tên và lưu
        existingCategory.setName(newName);
        return categoryRepository.save(existingCategory);
    }

    // == DELETE ==
    public void deleteCategory(Long categoryId) {
        // 1. Kiểm tra xem category có tồn tại không
        if (!categoryRepository.existsById(categoryId)) {
            throw new WebErrorConfig(ErrorCode.CATEGORY_NOT_FOUND);
        }

        // 2. Xóa
        categoryRepository.deleteById(categoryId);
    }
}

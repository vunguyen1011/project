package com.thanglong.project.usecase.Service;

import com.thanglong.project.domain.ENUM.ErrorCode;
import com.thanglong.project.domain.model.AttributeModel;
import com.thanglong.project.domain.model.CategoryModel;
import com.thanglong.project.domain.repository.AttributeRepository;
import com.thanglong.project.domain.repository.CategoryRepository;
import com.thanglong.project.usecase.DTO.Request.CategoryRequest;
import com.thanglong.project.usecase.Exception.WebErrorConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final AttributeRepository attributeRepository;

    public CategoryModel createCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName()))
            throw new WebErrorConfig(ErrorCode.CATEGORY_ALREADY_EXITED); // <-- Cần ErrorCode này

        CategoryModel categoryModel = CategoryModel.builder()
                .name(request.getName())
                .parentId(request.getParentId())
                .build();
        return categoryRepository.save(categoryModel);
    }


    public CategoryModel getCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.CATEGORY_NOT_FOUND)); // <-- Cần ErrorCode này
    }

    // == READ (Get all) ==
    public List<CategoryModel> getAllCategories() {
        return categoryRepository.findAll();
    }

    // == UPDATE ==
    public CategoryModel updateCategory(Integer categoryId,CategoryRequest request) {
        // 1. Tìm category hiện có
        CategoryModel existingCategory = getCategoryById(categoryId); // Tái sử dụng hàm getById

        // 2. Kiểm tra xem tên mới đã tồn tại ở category khác chưa
        if (!existingCategory.getName().equals(request.getName()) && categoryRepository.existsByName(request.getName())) {
            throw new WebErrorConfig(ErrorCode.CATEGORY_ALREADY_EXITED);
        }

        // 3. Cập nhật tên và lưu
        existingCategory.setName(request.getName());
        existingCategory.setParentId(request.getParentId());
        return categoryRepository.save(existingCategory);
    }

    // == DELETE ==
    public void deleteCategory(Integer categoryId) {
        // 1. Kiểm tra xem category có tồn tại không
        if (!categoryRepository.existsById(categoryId)) {
            throw new WebErrorConfig(ErrorCode.CATEGORY_NOT_FOUND);
        }

        // 2. Xóa
        categoryRepository.deleteById(categoryId);
    }
   public List<AttributeModel> findAttributeByCategoryId(Integer categoryId){
        return attributeRepository.findByCategoryId(categoryId);
    }
}

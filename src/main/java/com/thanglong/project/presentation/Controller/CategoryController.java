package com.thanglong.project.presentation.Controller;

import com.thanglong.project.domain.model.CategoryModel;
import com.thanglong.project.usecase.DTO.Request.CategoryRequest;
import com.thanglong.project.usecase.DTO.Response.ApiResponse;
import com.thanglong.project.usecase.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories") // <-- Đường dẫn gốc
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    // == CREATE (POST /categories) ==
    @PostMapping
    public ApiResponse<CategoryModel> createCategory(@RequestBody CategoryRequest request) {
        // Gọi service chỉ với 'name', theo 'CategoryService' đã tạo
        CategoryModel categoryModel = categoryService.createCategory(request.getName());

        return ApiResponse.<CategoryModel>builder()
                .code(201) // 201 Created
                .data(categoryModel)
                .message("Create category successfully")
                .build();
    }

    // == READ (GET /categories/{categoryId}) ==
    @GetMapping("/{categoryId}")
    public ApiResponse<CategoryModel> getCategoryById(@PathVariable Long categoryId) {
        CategoryModel categoryModel = categoryService.getCategoryById(categoryId);
        return ApiResponse.<CategoryModel>builder()
                .code(200)
                .data(categoryModel)
                .message("Get category successfully")
                .build();
    }

    // == READ (GET /categories) ==
    @GetMapping
    public ApiResponse<List<CategoryModel>> getAllCategories() {
        List<CategoryModel> categories = categoryService.getAllCategories();
        return ApiResponse.<List<CategoryModel>>builder()
                .code(200)
                .data(categories)
                .message("Get all categories successfully")
                .build();
    }

    // == UPDATE (PUT /categories/{categoryId}) ==
    @PutMapping("/{categoryId}")
    public ApiResponse<CategoryModel> updateCategory(
            @PathVariable Long categoryId,
            @RequestBody CategoryRequest request) {

        // Gọi service chỉ với 'name', theo 'CategoryService' đã tạo
        CategoryModel updatedCategory = categoryService.updateCategory(categoryId, request.getName());

        return ApiResponse.<CategoryModel>builder()
                .code(200)
                .data(updatedCategory)
                .message("Update category successfully")
                .build();
    }

    // == DELETE (DELETE /categories/{categoryId}) ==
    @DeleteMapping("/{categoryId}")
    public ApiResponse<String> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ApiResponse.<String>builder()
                .code(200)
                // Không cần data khi xóa
                .message("Delete category successfully")
                .build();
    }
}

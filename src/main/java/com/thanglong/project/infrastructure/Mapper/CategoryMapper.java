package com.thanglong.project.infrastructure.Mapper;

import com.thanglong.project.domain.model.CategoryModel;
import com.thanglong.project.infrastructure.Entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryModel toModel(Category entity) {
        if (entity == null) {
            return null;
        }

        return CategoryModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .parentId(entity.getParentId())
                .build();
    }

    public Category toEntity(CategoryModel model) {
        if (model == null) {
            return null;
        }

        return Category.builder()
                .id(model.getId()) // ID có thể null nếu đây là đối tượng mới
                .name(model.getName())
                .parentId(model.getParentId())
                .build();
    }
}

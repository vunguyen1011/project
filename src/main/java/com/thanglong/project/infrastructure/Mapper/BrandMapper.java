package com.thanglong.project.infrastructure.Mapper;

import com.thanglong.project.domain.model.BrandModel;
import com.thanglong.project.infrastructure.Entity.Brand;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {

    public BrandModel toModel(Brand entity) {
        if (entity == null) {
            return null;
        }
        return BrandModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                // ... map các trường khác nếu có
                .build();
    }


    public Brand toEntity(BrandModel model) {
        if (model == null) {
            return null;
        }
        return Brand.builder()
                .id(model.getId()) // ID có thể null nếu đây là đối tượng mới
                .name(model.getName())
                // ... map các trường khác nếu có
                .build();
    }
}

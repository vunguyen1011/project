package com.thanglong.project.infrastructure.Mapper;

import com.thanglong.project.domain.model.ProviderModel; // Giả định bạn có model này
import com.thanglong.project.infrastructure.Entity.Provider; // Giả định bạn có entity này
import org.springframework.stereotype.Component;


@Component // (1) Đánh dấu là 1 Spring Bean
public class ProviderMapper {

    public ProviderModel toModel(Provider entity) {
        if (entity == null) {
            return null;
        }
        return ProviderModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public Provider toEntity(ProviderModel model) {
        if (model == null) {
            return null;
        }
        Provider entity = new Provider();
        entity.setId(model.getId());
        entity.setName(model.getName());
        return entity;
    }
}
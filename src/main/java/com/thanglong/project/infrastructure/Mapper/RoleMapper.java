package com.thanglong.project.infrastructure.Mapper;

import com.thanglong.project.domain.model.RoleModel;
import com.thanglong.project.infrastructure.Entity.Role;
import org.springframework.stereotype.Component;

@Component // (1) Đánh dấu là Spring Bean
public class RoleMapper {

    public RoleModel toModel(Role entity) {
        if (entity == null) {
            return null;
        }
        return RoleModel.builder()
                .id(entity.getId())
                .name(entity.getName())

                .build();
    }
    public Role toEntity(RoleModel model) {
        if (model == null) {
            return null;
        }
        Role entity = new Role();
        entity.setId(model.getId());
        entity.setName(model.getName()); // Giả sử tên trường là "name"
        return entity;
    }
}
package com.thanglong.project.infrastructure.Mapper;

import com.thanglong.project.domain.model.UserModel;
import com.thanglong.project.infrastructure.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final RoleMapper roleMapper;
    private final ProviderMapper providerMapper;
    public UserModel toModel(User entity) {
        if (entity == null) {
            return null;
        }

        return UserModel.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .gender(entity.isGender())
                .phone(entity.getPhone())
                .isActive(entity.isActive())
                .roles(entity.getRoles() == null ? new HashSet<>() :
                        entity.getRoles().stream()
                                .map(roleMapper::toModel)
                                .collect(Collectors.toSet()))
                // (3) Bổ sung logic map cho providers
                .providers(entity.getProviders() == null ? new HashSet<>() :
                        entity.getProviders().stream()
                                .map(providerMapper::toModel)
                                .collect(Collectors.toSet()))
                .build();
    }

    public User toEntity(UserModel model) {
        if (model == null) {
            return null;
        }

        User entity = new User();
        entity.setId(model.getId());
        entity.setFirstName(model.getFirstName());
        entity.setLastName(model.getLastName());
        entity.setEmail(model.getEmail());
        entity.setUsername(model.getUsername());
        entity.setPassword(model.getPassword());
        entity.setGender(model.isGender());
        entity.setPhone(model.getPhone());
        entity.setActive(model.isActive());

        entity.setRoles(model.getRoles() == null ? new HashSet<>() :
                model.getRoles().stream()
                        .map(roleMapper::toEntity)
                        .collect(Collectors.toSet()));

        // (4) Bổ sung logic map cho providers
        entity.setProviders(model.getProviders() == null ? new HashSet<>() :
                model.getProviders().stream()
                        .map(providerMapper::toEntity)
                        .collect(Collectors.toSet()));

        return entity;
    }
}
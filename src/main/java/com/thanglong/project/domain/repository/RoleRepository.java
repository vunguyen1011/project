package com.thanglong.project.domain.repository;

import com.thanglong.project.domain.model.RoleModel;
import com.thanglong.project.infrastructure.Entity.Role;

import java.util.Optional;

public interface RoleRepository {
    Optional<RoleModel> findByName(String name);
}

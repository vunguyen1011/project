package com.thanglong.project.infrastructure.Impl;

import com.thanglong.project.domain.model.RoleModel;
import com.thanglong.project.domain.repository.RoleRepository;
import com.thanglong.project.infrastructure.Entity.Role; // Import Role Entity
import com.thanglong.project.infrastructure.Mapper.RoleMapper; // Import RoleMapper
import com.thanglong.project.infrastructure.repository.RoleJpaRepository; // Import JpaRepo
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor // (1) Thêm Lombok để tiêm (inject)
public class RoleRepositoryImpl implements RoleRepository { // (2) Sửa lỗi chính tả

    // (3) Tiêm 2 file cần thiết
    private final RoleJpaRepository roleJpaRepository;
    private final RoleMapper roleMapper;

    @Override
    public Optional<RoleModel> findByName(String name) {
        // (4) Dùng JpaRepository để tìm Role (Entity)
        Optional<Role> roleEntity = roleJpaRepository.findByName(name);

        // (5) Dùng Mapper để "dịch" từ Entity sang Model
        return roleEntity.map(roleMapper::toModel);
    }
}
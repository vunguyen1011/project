package com.thanglong.project.infrastructure.repository;

import com.thanglong.project.infrastructure.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleJpaRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByName(String name);
}

package com.thanglong.project.infrastructure.repository;

import com.thanglong.project.infrastructure.Entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandJpaRepository extends JpaRepository<Brand, Integer> {
    boolean existsByName(String name);
}

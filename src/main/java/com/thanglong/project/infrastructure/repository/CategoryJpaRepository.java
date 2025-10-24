package com.thanglong.project.infrastructure.repository;

import com.thanglong.project.infrastructure.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
}

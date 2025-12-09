package com.thanglong.project.infrastructure.repository;

import com.thanglong.project.infrastructure.Entity.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttributeJpaRepository extends JpaRepository<Attribute,Integer> {
    Optional<Attribute> findByName(String name);
    List<Attribute> findByCategoryId(Integer categoryId);

}

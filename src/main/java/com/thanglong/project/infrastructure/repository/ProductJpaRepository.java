package com.thanglong.project.infrastructure.repository;

import com.thanglong.project.infrastructure.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<Product,Long> {
    Optional<Product> findById(Long Id);
    List<Product> findAll();
    void deleteById(Long Id);
    boolean existsById(Long id);
    boolean existsByName(String name);


}

package com.thanglong.project.domain.repository;

import com.thanglong.project.domain.model.ProductModel;
import com.thanglong.project.infrastructure.Entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<ProductModel> findById(Long Id);
    ProductModel save(ProductModel productModel);
    List<ProductModel> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByName(String name);

}

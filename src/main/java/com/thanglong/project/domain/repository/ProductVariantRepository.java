package com.thanglong.project.domain.repository;

import com.thanglong.project.domain.model.ProductVariantModel;

import java.util.List;
import java.util.Optional;

public interface ProductVariantRepository {

    ProductVariantModel save(ProductVariantModel productVariantModel);

    Optional<ProductVariantModel> findById(Long id);

    Optional<ProductVariantModel> findBySku(String sku);

    List<ProductVariantModel> findAll();

    void deleteById(Long id);

    boolean existsBySku(String sku);
}
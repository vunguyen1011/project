package com.thanglong.project.domain.repository;

import com.thanglong.project.domain.model.BrandModel;

import java.util.List;
import java.util.Optional;

public interface BrandRepository {
    boolean existsByName(String username);
    BrandModel save(BrandModel brandModel);

    Optional<BrandModel> findById(Long brandId);

    List<BrandModel> findAll();

    boolean existsById(Long brandId);

    void deleteById(Long brandId);
}

package com.thanglong.project.domain.repository;

import com.thanglong.project.domain.model.AttributeModel;

import java.util.List;
import java.util.Optional;

public interface AttributeRepository {

    AttributeModel save(AttributeModel attribute);
    Optional<AttributeModel> findById(Integer id);
    Optional<AttributeModel> findByName(String name);
    List<AttributeModel> findAll();
    void deleteById(Integer id);
    List<AttributeModel> findByCategoryId(Integer categoryId);
}
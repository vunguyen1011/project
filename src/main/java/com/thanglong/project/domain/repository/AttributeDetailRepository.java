package com.thanglong.project.domain.repository;

import com.thanglong.project.domain.model.AttributeDetailModel;

import java.util.List;
import java.util.Optional;

public interface AttributeDetailRepository {
    AttributeDetailModel save(AttributeDetailModel attributeDetailModel);
    Optional<AttributeDetailModel> findById(Integer id);
    List<AttributeDetailModel> findAll();
    List<AttributeDetailModel> findByAttributeId(Integer attributeId);
    void deleteById(Integer id);
    boolean existsByAttributeIdAndValue(Integer attributeId, Object value);
    Optional<AttributeDetailModel> findByAttributeIdAndValue(Integer attributeId, Object value);}
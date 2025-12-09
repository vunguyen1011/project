package com.thanglong.project.infrastructure.Impl;

import com.thanglong.project.domain.model.AttributeModel;
import com.thanglong.project.domain.repository.AttributeRepository;
import com.thanglong.project.infrastructure.Entity.Attribute; // Đổi tên Entity cho phù hợp
import com.thanglong.project.infrastructure.repository.AttributeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AttributeRepositoryImpl implements AttributeRepository {

    private final AttributeJpaRepository jpaRepository;

    @Override
    public AttributeModel save(AttributeModel attributeModel) {
        Attribute attributeEntity = toEntity(attributeModel);
        Attribute savedEntity = jpaRepository.save(attributeEntity);
        return toModel(savedEntity);
    }



    @Override
    public Optional<AttributeModel> findById(Integer id) {
        return jpaRepository.findById(id)
                .map(this::toModel); // Sử dụng map để chuyển đổi nếu Optional không rỗng
    }

    @Override
    public Optional<AttributeModel> findByName(String name) {
        return jpaRepository.findByName(name)
                .map(this::toModel);
    }

    @Override
    public List<AttributeModel> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toModel) // Chuyển đổi từng entity trong list
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        jpaRepository.deleteById(id);
    }

    private Attribute toEntity(AttributeModel model) {
        Attribute entity = new Attribute();
        entity.setName(model.getName());
        entity.setCategoryId(model.getCategoryId());
        entity.setType(model.getType());
        entity.setInputType(model.getInputType());
        return entity;
    }

    private AttributeModel toModel(Attribute entity) {
        return AttributeModel.builder()
                .id(entity.getId())
                .inputType(entity.getInputType())
                .name(entity.getName())
                .categoryId(entity.getCategoryId())
                .type(entity.getType())
                .build();
    }

    @Override
    public List<AttributeModel> findByCategoryId(Integer categoryId) {
        return  jpaRepository.findByCategoryId(categoryId).stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
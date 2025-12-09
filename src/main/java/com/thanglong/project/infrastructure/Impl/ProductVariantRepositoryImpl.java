package com.thanglong.project.infrastructure.Impl;

import com.thanglong.project.domain.ENUM.ErrorCode;
import com.thanglong.project.domain.model.AttributeDetailModel;
import com.thanglong.project.domain.model.ImageModel;
import com.thanglong.project.domain.model.ProductVariantModel;
import com.thanglong.project.domain.repository.ProductVariantRepository;
import com.thanglong.project.infrastructure.Entity.AttributeDetail;
import com.thanglong.project.infrastructure.Entity.Image;
import com.thanglong.project.infrastructure.Entity.ProductVariant;
import com.thanglong.project.infrastructure.Mapper.ImageMapper;
import com.thanglong.project.infrastructure.repository.AttributeDetailJpaRepository;
import com.thanglong.project.infrastructure.repository.ProductVariantJpaRepository;
import com.thanglong.project.usecase.Exception.WebErrorConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductVariantRepositoryImpl implements ProductVariantRepository {

    private final ProductVariantJpaRepository jpaRepository;
    private final AttributeDetailJpaRepository attributeDetailJpaRepository;
    private final ImageMapper imageMapper;

    @Override
    public ProductVariantModel save(ProductVariantModel model) {
        ProductVariant entityToSave;

        if (model.getId() != null) {
            entityToSave = jpaRepository.findById(model.getId())
                    .orElseThrow(() -> new WebErrorConfig(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));
        } else {
            entityToSave = new ProductVariant();
        }

        mapModelToEntity(model, entityToSave);

        ProductVariant savedEntity = jpaRepository.save(entityToSave);
        return toModel(savedEntity);
    }

    @Override
    public Optional<ProductVariantModel> findById(Long id) {
        return jpaRepository.findById(id).map(this::toModel);
    }

    @Override
    public Optional<ProductVariantModel> findBySku(String sku) {
        return jpaRepository.findBySku(sku).map(this::toModel);
    }

    @Override
    public List<ProductVariantModel> findAll() {
        return jpaRepository.findAll().stream().map(this::toModel).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsBySku(String sku) {
        return jpaRepository.existsBySku(sku);
    }

    private ProductVariantModel toModel(ProductVariant entity) {
        if (entity == null) {
            return null;
        }



        List<AttributeDetailModel> attributeModels = entity.getAttributes().stream()
                .map(this::attributeDetailToModel)
                .collect(Collectors.toList());
        List<ImageModel> imageModels = entity.getImages().stream()
                .map(imageMapper::toModel)
                .collect(Collectors.toList());
        return ProductVariantModel.builder()
                .id(entity.getId())
                .productId(entity.getProductId())
                .name(entity.getName())
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .costPrice(entity.getCostPrice())
                .sellPrice(entity.getSellPrice())
                .quantitySold(entity.getQuantitySold())
                .sku(entity.getSku())
                .images(imageModels)
                .attributes(attributeModels)
                .build();
    }

    private void mapModelToEntity(ProductVariantModel model, ProductVariant entity) {
        entity.setProductId(model.getProductId());
        entity.setName(model.getName());
        entity.setPrice(model.getPrice());
        entity.setQuantity(model.getQuantity());
        entity.setCostPrice(model.getCostPrice());
        entity.setSellPrice(model.getSellPrice());
        entity.setQuantitySold(model.getQuantitySold());
        entity.setSku(model.getSku());

        if (model.getImages() != null) {
            entity.getImages().clear();
            List<Image> newImages = model.getImages().stream()
                    .map(imageModel -> imageMapper.toEntity(imageModel))
                    .collect(Collectors.toList());
            entity.getImages().addAll(newImages);
        } else {
            entity.setImages(Collections.emptyList());
        }

        if (model.getAttributes() != null) {
            entity.getAttributes().clear();
            List<AttributeDetail> newAttributes = model.getAttributes().stream()
                    .map(attrModel -> attributeDetailJpaRepository.findById(attrModel.getId())
                            .orElseThrow(() -> new WebErrorConfig(ErrorCode.ATTRIBUTE_DETAIL_NOT_FOUND)))
                    .collect(Collectors.toList());
            entity.getAttributes().addAll(newAttributes);
        } else {
            entity.setAttributes(Collections.emptyList());
        }
    }

    private AttributeDetailModel attributeDetailToModel(AttributeDetail entity) {
        AttributeDetailModel model = new AttributeDetailModel();
        model.setId(entity.getId());
        model.setAttributeId(entity.getAttributeId());

        if (entity.getTextValue() != null) {
            model.setValue(entity.getTextValue());
        } else if (entity.getNumberValue() != null) {
            model.setValue(entity.getNumberValue());
        } else if (entity.getDecimalValue() != null) {
            model.setValue(entity.getDecimalValue());
        } else if (entity.getBooleanValue() != null) {
            model.setValue(entity.getBooleanValue());
        } else if (entity.getDateValue() != null) {
            model.setValue(entity.getDateValue());
        } else if (entity.getDatetimeValue() != null) {
            model.setValue(entity.getDatetimeValue());
        }

        return model;
    }
}
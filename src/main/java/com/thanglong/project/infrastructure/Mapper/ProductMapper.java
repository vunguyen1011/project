package com.thanglong.project.infrastructure.Mapper;


import com.thanglong.project.domain.model.ProductModel;
import com.thanglong.project.infrastructure.Entity.Product;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Mapper chịu trách nhiệm chuyển đổi dữ liệu giữa
 * Product Entity (lớp hạ tầng) và ProductModel (lớp domain).
 */
@Component // Đánh dấu là một Spring Bean để có thể được inject vào các service khác
public class ProductMapper {

    /**
     * Chuyển đổi từ Product Entity sang ProductModel.
     *
     * @param entity Đối tượng Product lấy từ cơ sở dữ liệu.
     * @return Một đối tượng ProductModel chứa thông tin nghiệp vụ.
     */
    public ProductModel toModel(Product entity) {
        if (entity == null) {
            return null;
        }

        return ProductModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                 //.weight(entity.getWeight())
                .image(entity.getImage())
                .averageRating(entity.getAverageRating())
                .totalSold(entity.getTotalSold())
                .description(entity.getDescription())
                .isActive(entity.isActive())
                .brandId(entity.getBrandId())
                .categoryId(entity.getCategoryId())

                .build();
    }

    public Product toEntity(ProductModel model) {
        if (model == null) {
            return null;
        }

        Product entity = new Product();
        entity.setId(model.getId());
        entity.setName(model.getName());
//        entity.setWeight(model.getWeight());
        entity.setImage(model.getImage());
        entity.setAverageRating(model.getAverageRating());
        entity.setTotalSold(model.getTotalSold());
        entity.setDescription(model.getDescription());
        entity.setActive(model.isActive());
        entity.setBrandId(model.getBrandId());
        entity.setCategoryId(model.getCategoryId());

        return entity;
    }
}
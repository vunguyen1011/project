package com.thanglong.project.infrastructure.Impl;

import com.thanglong.project.domain.model.ProductModel;
import com.thanglong.project.domain.repository.ProductRepository;
import com.thanglong.project.infrastructure.Mapper.ProductMapper;
import com.thanglong.project.infrastructure.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component("productRepositoryImpl") // Đặt tên bean để tránh nhầm lẫn
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository; // Công cụ thực thi CSDL
    private final ProductMapper productMapper;               // Công cụ chuyển đổi

    @Override
    public ProductModel save(ProductModel productModel) {
        var productEntity = productMapper.toEntity(productModel);
        var savedEntity = productJpaRepository.save(productEntity);
        return productMapper.toModel(savedEntity);
    }

    @Override
    public Optional<ProductModel> findById(Long id) {
        var optionalProductEntity = productJpaRepository.findById(id);
        return optionalProductEntity.map(productMapper::toModel);
    }

    @Override
    public List<ProductModel> findAll() {
        var entities = productJpaRepository.findAll();
        return entities.stream()
                .map(productMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        productJpaRepository.deleteById(id);
    }
    @Override
    public boolean existsById(Long id) {
        return productJpaRepository.existsById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return  productJpaRepository.existsByName(name);
    }
}
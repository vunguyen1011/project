package com.thanglong.project.infrastructure.Impl;

import com.thanglong.project.domain.model.BrandModel;
import com.thanglong.project.domain.repository.BrandRepository;
import com.thanglong.project.infrastructure.Entity.Brand;
import com.thanglong.project.infrastructure.Mapper.BrandMapper;
import com.thanglong.project.infrastructure.repository.BrandJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository // Đánh dấu đây là một Spring component ở tầng repository
@RequiredArgsConstructor
public class BrandRepositoryImpl implements BrandRepository {
    // Inject Spring Data JpaRepository
    private final BrandJpaRepository brandJpaRepository;
    private final BrandMapper brandMapper;

    @Override
    public boolean existsByName(String name) {
        // Sửa tham số từ 'username' thành 'name' cho đúng ngữ nghĩa
        // Gọi thẳng phương thức của JpaRepository
        return brandJpaRepository.existsByName(name);
    }

    @Override
    public BrandModel save(BrandModel brandModel) {
        // 1. Chuyển đổi từ Domain Model sang JPA Entity
        Brand brandEntity = brandMapper.toEntity(brandModel);
        // 2. Dùng JpaRepository để lưu vào DB
        Brand savedEntity = brandJpaRepository.save(brandEntity);
        // 3. Chuyển đổi Entity đã lưu (có thể đã được gán ID) trở lại Domain Model
        return brandMapper.toModel(savedEntity);
    }

    @Override
    public Optional<BrandModel> findById(Integer brandId) {
        // 1. Tìm Entity bằng JpaRepository
        Optional<Brand> entityOptional = brandJpaRepository.findById(brandId);

        // 2. SỬ DỤNG MAPPER: Map Optional<Entity> sang Optional<Model>
        return entityOptional.map(brandMapper::toModel); // (hoặc .map(entity -> brandMapper.toModel(entity)))
    }

    @Override
    public List<BrandModel> findAll() {
        // 1. Lấy tất cả Entities
        List<Brand> entities = brandJpaRepository.findAll();

        // 2. SỬ DỤNG MAPPER: Chuyển đổi List<Entity> sang List<Model>
        return entities.stream()
                .map(brandMapper::toModel) // (hoặc .map(entity -> brandMapper.toModel(entity)))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Integer brandId) {
        // Gọi thẳng phương thức của JpaRepository
        return brandJpaRepository.existsById(brandId);
    }

    @Override
    public void deleteById(Integer brandId) {
        // Gọi thẳng phương thức của JpaRepository
        brandJpaRepository.deleteById(brandId);
    }

}

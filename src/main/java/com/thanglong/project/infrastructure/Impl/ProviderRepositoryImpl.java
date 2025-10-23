package com.thanglong.project.infrastructure.Impl;

import com.thanglong.project.domain.model.ProviderModel;
import com.thanglong.project.domain.repository.ProviderRepository;
import com.thanglong.project.infrastructure.Entity.Provider; // 1. Import Entity
import com.thanglong.project.infrastructure.Mapper.ProviderMapper; // 2. Import Mapper
import com.thanglong.project.infrastructure.repository.ProviderJpaRepository; // 3. Import JpaRepo
import lombok.RequiredArgsConstructor; // 4. Import Lombok
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProviderRepositoryImpl implements ProviderRepository {
    private final ProviderJpaRepository providerJpaRepository;
    private final ProviderMapper providerMapper;
    @Override
    public Optional<ProviderModel> findByName(String name) {
        Optional<Provider> entity = providerJpaRepository.findByName(name);
        return entity.map(providerMapper::toModel);
    }
}
package com.thanglong.project.infrastructure.Impl;


import com.thanglong.project.domain.model.ImageModel;
import com.thanglong.project.domain.repository.ImageRepository;
import com.thanglong.project.infrastructure.Mapper.ImageMapper;
import com.thanglong.project.infrastructure.repository.ImageJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepository {
    private final ImageJpaRepository imageJpaRepository;
    private final ImageMapper imageMapper;
    @Override
    public ImageModel save(ImageModel imageModel) {
        var entity = imageMapper.toEntity(imageModel);
        var savedEntity = imageJpaRepository.save(entity);
        return imageMapper.toModel(savedEntity);
    }
}

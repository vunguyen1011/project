package com.thanglong.project.infrastructure.Mapper;

import com.thanglong.project.domain.model.ImageModel;
import com.thanglong.project.infrastructure.Entity.Image;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {
    public ImageModel toModel(Image image) {
        return ImageModel.builder()
                .id(image.getId())
                .url(image.getUrl())
                .productId(image.getProductId())
                .build();
    }
    public Image toEntity(ImageModel imageModel) {
        Image image = new Image();
        image.setId(imageModel.getId());
        image.setUrl(imageModel.getUrl());
        image.setProductId(imageModel.getProductId());
        return image;
    }
}

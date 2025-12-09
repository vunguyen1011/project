package com.thanglong.project.domain.repository;

import com.thanglong.project.domain.model.ImageModel;

public interface ImageRepository {
    ImageModel save(ImageModel imageModel);
}

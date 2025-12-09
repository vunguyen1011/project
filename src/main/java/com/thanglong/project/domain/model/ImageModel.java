package com.thanglong.project.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageModel {
    private Long id;
    private String url;
    private Long productId;
    private Long productVariantId;
}

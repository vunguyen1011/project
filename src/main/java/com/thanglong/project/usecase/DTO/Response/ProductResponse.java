package com.thanglong.project.usecase.DTO.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private String brandName;
    private String categoryName;
    private String imageUrl;
    private Long totalSold;
    private Boolean isActive;
}

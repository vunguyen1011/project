package com.thanglong.project.usecase.DTO.Request;

import lombok.Data;
import org.apache.commons.lang3.IntegerRange;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private Integer brandId;
    private Integer categoryId;
    private MultipartFile image;
}

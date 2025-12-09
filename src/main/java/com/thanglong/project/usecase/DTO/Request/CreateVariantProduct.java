package com.thanglong.project.usecase.DTO.Request;

import com.thanglong.project.domain.model.AttributeDetailModel;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateVariantProduct {
    private Long productId;
    private String name;
    private BigDecimal price;
    private int quantity;
    private BigDecimal costPrice;
    private BigDecimal sellPrice;
    private List<MultipartFile> images; // Dùng MultipartFile để nhận file upload
    private List<AttributeDetailModel> attributes;
}

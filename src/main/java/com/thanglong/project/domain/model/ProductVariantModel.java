package com.thanglong.project.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantModel {
    private Long id;
    private Long productId;
    private String name;
    private BigDecimal price;
    private int quantity;
    private BigDecimal costPrice;
    private BigDecimal sellPrice;
    private Long quantitySold;
    private List<ImageModel> images;
    private List<AttributeDetailModel> attributes;

//    private List<VoucherModel> vouchers;
}
package com.thanglong.project.domain.model;

import com.thanglong.project.infrastructure.Entity.ProductVariant;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductModel {
    private  Long id; // ID có thể là final nếu nó không thay đổi sau khi tạo
    private String name;
    private String image;
    private Float averageRating;
    private Long totalSold;
    private String description;
    private boolean isActive=true;
    private Integer brandId;
    private Integer categoryId;
    public void isDeleted(){
        this.isActive=false;
    }





//    private List<ProductVariant> variants;
}

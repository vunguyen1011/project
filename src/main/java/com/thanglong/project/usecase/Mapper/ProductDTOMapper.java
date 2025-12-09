package com.thanglong.project.usecase.Mapper;

import com.thanglong.project.domain.ENUM.ErrorCode;
import com.thanglong.project.domain.model.ProductModel;
import com.thanglong.project.domain.repository.BrandRepository;
import com.thanglong.project.domain.repository.CategoryRepository;
import com.thanglong.project.usecase.DTO.Request.ProductRequest;
import com.thanglong.project.usecase.DTO.Response.ProductResponse;
import com.thanglong.project.usecase.Exception.WebErrorConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductDTOMapper {
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    public ProductModel toModel(ProductRequest request) {
        if (request == null) {
            return null;
        }
        if(!brandRepository.existsById(request.getBrandId())) throw  new WebErrorConfig(ErrorCode.BRAND_NOT_FOUND);
        if(!categoryRepository.existsById(request.getCategoryId())) throw new WebErrorConfig(ErrorCode.CATEGORY_NOT_FOUND);


        // Tạo ProductModel từ dữ liệu của request
        return ProductModel.builder()
                .name(request.getName())
                .description(request.getDescription())
                .brandId(request.getBrandId())
                .categoryId(request.getCategoryId())
                .totalSold(0L)
                .isActive(true)
                .build();
    }
    public ProductResponse toResponse(ProductModel model) {

            String brandName = brandRepository.findById(model.getBrandId())
                    .map(b -> b.getName())
                    .orElse("Unknown Brand");

            String categoryName = categoryRepository.findById(model.getCategoryId())
                    .map(c -> c.getName())
                    .orElse("Unknown Category");

            return ProductResponse.builder()
                    .id(model.getId())
                    .name(model.getName())
                    .description(model.getDescription())
                    .brandName(brandName)
                    .categoryName(categoryName)
                    .imageUrl(model.getImage())
                    .totalSold(model.getTotalSold())
                    .isActive(model.isActive())
                    .build();
        }
}

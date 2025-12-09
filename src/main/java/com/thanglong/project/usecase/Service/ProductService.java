package com.thanglong.project.usecase.Service;

import com.thanglong.project.domain.ENUM.ErrorCode;
import com.thanglong.project.domain.model.ProductModel;
import com.thanglong.project.domain.repository.BrandRepository;
import com.thanglong.project.domain.repository.CategoryRepository;
import com.thanglong.project.domain.repository.ProductRepository;
import com.thanglong.project.usecase.DTO.Request.ProductRequest;
import com.thanglong.project.usecase.DTO.Response.ProductResponse;
import com.thanglong.project.usecase.Exception.WebErrorConfig;
import com.thanglong.project.usecase.Mapper.ProductDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductDTOMapper productRequestMapper;
    private final ImageService imageService;


    // có thể tối ưu hơn vì  chưa roolback nếu upload image fail
    public ProductModel createProduct(ProductRequest request) throws IOException {
        if(productRepository.existsByName(request.getName()))
            throw new WebErrorConfig(ErrorCode.PRODUCT_ALREADY_EXITED);
        ProductModel productModel = productRequestMapper.toModel(request);
        ProductModel savedProduct = productRepository.save(productModel);
        imageService.uploadImageForProduct(request.getImage().getBytes(), savedProduct.getId());
        return savedProduct;
    }
    public List<ProductResponse> getAllProducts(){
        return productRepository.findAll().stream()
                .map(productRequestMapper::toResponse)
                .collect(Collectors.toList());    }
}

package com.thanglong.project.presentation.Controller;

import com.thanglong.project.domain.model.ProductModel;
import com.thanglong.project.usecase.DTO.Request.ProductRequest;
import com.thanglong.project.usecase.DTO.Response.ApiResponse;
import com.thanglong.project.usecase.DTO.Response.ProductResponse;
import com.thanglong.project.usecase.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    @PostMapping
    public ApiResponse<ProductModel> createProduct(@ModelAttribute ProductRequest request) throws IOException {
        return ApiResponse.<ProductModel>builder()
                .data(productService.createProduct(request))
                .message("Product created successfully")
                .build();

    }
    @GetMapping
    public ApiResponse<List<ProductResponse>> getAllProducts(){
        return ApiResponse.<List<ProductResponse>>builder()
                .data(productService.getAllProducts())
                .message("Get all products successfully")
                .build();
    }
}

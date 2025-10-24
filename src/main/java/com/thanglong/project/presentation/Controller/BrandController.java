package com.thanglong.project.presentation.Controller;

import com.thanglong.project.domain.model.BrandModel;
import com.thanglong.project.usecase.DTO.Request.BrandRequest;
import com.thanglong.project.usecase.DTO.Response.ApiResponse;
import com.thanglong.project.usecase.Service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    // === CREATE (POST /brands) ===
    @PostMapping
    public ApiResponse<BrandModel> createBrand(@RequestBody BrandRequest request) {
        BrandModel brandModel = brandService.createBrand(request);
        return ApiResponse.<BrandModel>builder()
                .code(200)
                .data(brandModel)
                .message("Create brand successfully")
                .build();
    }

    // === READ (GET /brands/{id}) ===
    @GetMapping("/{brandId}")
    public ApiResponse<BrandModel> getBrandById(@PathVariable Integer brandId) {
        BrandModel brandModel = brandService.getBrandById(brandId);
        return ApiResponse.<BrandModel>builder()
                .code(200)
                .data(brandModel)
                .message("Get brand successfully")
                .build();
    }

    // === READ ALL (GET /brands) ===
    @GetMapping
    public ApiResponse<List<BrandModel>> getAllBrands() {
        List<BrandModel> brands = brandService.getAllBrands();
        return ApiResponse.<List<BrandModel>>builder()
                .code(200)
                .data(brands)
                .message("Get all brands successfully")
                .build();
    }

    // === UPDATE (PUT /brands/{id}) ===
    @PutMapping("/{brandId}")
    public ApiResponse<BrandModel> updateBrand(
            @PathVariable Integer brandId,
            @RequestBody BrandRequest request) {

        BrandModel updatedBrand = brandService.updateBrand(brandId, request);
        return ApiResponse.<BrandModel>builder()
                .code(200)
                .data(updatedBrand)
                .message("Update brand successfully")
                .build();
    }

    // === DELETE (DELETE /brands/{id}) ===
    @DeleteMapping("/{brandId}")
    public ApiResponse<String> deleteBrand(@PathVariable Integer brandId) {
        brandService.deleteBrand(brandId);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Delete brand successfully")
                .build();
    }
}

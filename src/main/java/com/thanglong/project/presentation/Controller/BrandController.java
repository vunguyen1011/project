package com.thanglong.project.presentation.Controller;

import com.thanglong.project.domain.model.BrandModel;
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
    @PostMapping("/brand")
    public ApiResponse<BrandModel> createBrand(@RequestBody String name){
        BrandModel brandModel = brandService.createBrand(name);
        return ApiResponse.<BrandModel>builder()
                .code(200)
                .data(brandModel)
                .message("Create brand successfully")
                .build();
    }

    @GetMapping("/{brandId}")
    public ApiResponse<BrandModel> getBrandById(@PathVariable Long brandId) {
        BrandModel brandModel = brandService.getBrandById(brandId);
        return ApiResponse.<BrandModel>builder()
                .code(200)
                .data(brandModel)
                .message("Get brand successfully")
                .build();
    }

    // == READ (GET /brands) ==
    @GetMapping
    public ApiResponse<List<BrandModel>> getAllBrands() {
        List<BrandModel> brands = brandService.getAllBrands();
        return ApiResponse.<List<BrandModel>>builder()
                .code(200)
                .data(brands)
                .message("Get all brands successfully")
                .build();
    }

    // == UPDATE (PUT /brands/{brandId}) ==
    @PutMapping("/{brandId}")
    public ApiResponse<BrandModel> updateBrand(
            @PathVariable Long brandId,
            @RequestBody String name) {

        BrandModel updatedBrand = brandService.updateBrand(brandId, name);
        return ApiResponse.<BrandModel>builder()
                .code(200)
                .data(updatedBrand)
                .message("Update brand successfully")
                .build();
    }

    // == DELETE (DELETE /brands/{brandId}) ==
    @DeleteMapping("/{brandId}")
    public ApiResponse<String> deleteBrand(@PathVariable Long brandId) {
        brandService.deleteBrand(brandId);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Delete brand successfully")
                .build();
    }
}

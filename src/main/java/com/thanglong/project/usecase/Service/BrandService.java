package com.thanglong.project.usecase.Service;

import com.thanglong.project.domain.ENUM.ErrorCode;
import com.thanglong.project.domain.model.BrandModel;
import com.thanglong.project.domain.repository.BrandRepository;
import com.thanglong.project.usecase.DTO.Request.BrandRequest;
import com.thanglong.project.usecase.Exception.WebErrorConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandModel createBrand (BrandRequest request){
        if(brandRepository.existsByName(request.getName()))
            throw new WebErrorConfig(ErrorCode.BRAND_ALREADY_EXITED);
        BrandModel brandModel = BrandModel.builder()
                .name(request.getName())
                .build();
        return brandRepository.save(brandModel);
    }

    public BrandModel getBrandById(Integer brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.BRAND_NOT_FOUND)); // Bạn sẽ cần thêm ErrorCode này
    }

    // == READ (Get all) ==
    public List<BrandModel> getAllBrands() {
        return brandRepository.findAll();
    }

    // == UPDATE ==
    public BrandModel updateBrand(Integer brandId, BrandRequest request) {
        BrandModel existingBrand = getBrandById(brandId); // Tái sử dụng hàm getById để xử lý "not found"


        if (!existingBrand.getName().equals(request.getName()) && brandRepository.existsByName(request.getName())) {
            throw new WebErrorConfig(ErrorCode.BRAND_ALREADY_EXITED);
        }

        // 3. Cập nhật tên và lưu
        existingBrand.setName(request.getName());
        return brandRepository.save(existingBrand);
    }

    // == DELETE ==
    public void deleteBrand(Integer brandId) {
        // 1. Kiểm tra xem brand có tồn tại không trước khi xóa
        if (!brandRepository.existsById(brandId)) {
            throw new WebErrorConfig(ErrorCode.BRAND_NOT_FOUND);
        }

        brandRepository.deleteById(brandId);
    }
}

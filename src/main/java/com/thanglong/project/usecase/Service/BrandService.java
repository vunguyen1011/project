package com.thanglong.project.usecase.Service;

import com.thanglong.project.domain.ENUM.ErrorCode;
import com.thanglong.project.domain.model.BrandModel;
import com.thanglong.project.domain.repository.BrandRepository;
import com.thanglong.project.usecase.Exception.WebErrorConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandModel createBrand (String name){
        if(brandRepository.existsByName(name))
            throw new WebErrorConfig(ErrorCode.BRAND_ALREADY_EXITED);
        BrandModel brandModel = BrandModel.builder()
                .name(name)
                .build();
        return brandRepository.save(brandModel);
    }

    public BrandModel getBrandById(Long brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.BRAND_NOT_FOUND)); // Bạn sẽ cần thêm ErrorCode này
    }

    // == READ (Get all) ==
    public List<BrandModel> getAllBrands() {
        return brandRepository.findAll();
    }

    // == UPDATE ==
    public BrandModel updateBrand(Long brandId, String newName) {
        // 1. Tìm brand hiện có
        BrandModel existingBrand = getBrandById(brandId); // Tái sử dụng hàm getById để xử lý "not found"

        // 2. Kiểm tra xem tên mới đã tồn tại ở một brand khác chưa
        // Chỉ kiểm tra nếu tên thực sự thay đổi
        if (!existingBrand.getName().equals(newName) && brandRepository.existsByName(newName)) {
            throw new WebErrorConfig(ErrorCode.BRAND_ALREADY_EXITED);
        }

        // 3. Cập nhật tên và lưu
        existingBrand.setName(newName);
        return brandRepository.save(existingBrand);
    }

    // == DELETE ==
    public void deleteBrand(Long brandId) {
        // 1. Kiểm tra xem brand có tồn tại không trước khi xóa
        if (!brandRepository.existsById(brandId)) {
            throw new WebErrorConfig(ErrorCode.BRAND_NOT_FOUND);
        }

        // 2. Xóa
        brandRepository.deleteById(brandId);
    }
}

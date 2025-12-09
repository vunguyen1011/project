package com.thanglong.project.usecase.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.thanglong.project.domain.ENUM.ErrorCode;
import com.thanglong.project.domain.model.ImageModel;
import com.thanglong.project.domain.repository.ImageRepository; // SỬA 1: Dùng ImageRepository
import com.thanglong.project.domain.repository.ProductRepository;
import com.thanglong.project.domain.repository.ProductVariantRepository;
import com.thanglong.project.usecase.Exception.WebErrorConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageService {
    private final Cloudinary cloudinary;
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ImageRepository imageRepository; // SỬA 2: Inject ImageRepository (interface)
    @Async
    public void uploadImageForProduct(byte[] fileData, Long productId) throws IOException {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.PRODUCT_NOT_FOUND));

        Map<?, ?> uploadResult = cloudinary.uploader()
                .upload(fileData, ObjectUtils.emptyMap());
        String imageUrl = uploadResult.get("secure_url").toString();
        ImageModel imageToSave = ImageModel.builder()
                .url(imageUrl)
                .productId(productId)
                .build();
        ImageModel savedImage = imageRepository.save(imageToSave);
        product.setImage(savedImage.getUrl());
        productRepository.save(product);


    }
    @Async
    public void uploadMultipleImagesForProduct(List<byte[]> fileList, Long productVariantId) {
        // 1️⃣ Tìm biến thể sản phẩm
        var productVariant = productVariantRepository.findById(productVariantId)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));

        // 2️⃣ Danh sách ảnh đã upload thành công
        List<ImageModel> uploadedImages = new ArrayList<>();

        // 3️⃣ Upload từng file lên Cloudinary
        fileList.forEach(fileData -> {
            try {
                Map<?, ?> uploadResult = cloudinary.uploader()
                        .upload(fileData, ObjectUtils.emptyMap());

                String imageUrl = uploadResult.get("secure_url").toString();

                ImageModel image = ImageModel.builder()
                        .url(imageUrl)
                        .productId(productVariantId)
                        .build();

                // Lưu ảnh vào DB
                ImageModel saved = imageRepository.save(image);
                uploadedImages.add(saved);

            } catch (IOException e) {
                log.error("❌ Upload failed for variant {}: {}", productVariantId, e.getMessage());
            }
        });

        // 4️⃣ Gắn list ảnh vào biến thể sản phẩm
        if (!uploadedImages.isEmpty()) {
            productVariant.setImages(uploadedImages);
            productVariantRepository.save(productVariant);
            log.info("✅ Uploaded {} images for product variant {}", uploadedImages.size(), productVariantId);
        }
    }


}

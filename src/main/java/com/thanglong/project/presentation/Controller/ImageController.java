package com.thanglong.project.presentation.Controller;

import com.thanglong.project.domain.model.ImageModel;
import com.thanglong.project.usecase.DTO.Response.ApiResponse;
import com.thanglong.project.usecase.Service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/product") // Đường dẫn rõ ràng hơn
    public ApiResponse<ImageModel> uploadProductImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("productId") Long productId) throws IOException {
        {
            return ApiResponse.<ImageModel>builder()
                    .message("File is empty. Please select a file to upload.")
                    .build();
        }
    }
}

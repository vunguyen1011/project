package com.thanglong.project.presentation.Controller;

import com.thanglong.project.domain.model.AttributeModel;
import com.thanglong.project.usecase.DTO.Response.ApiResponse; // Import ApiResponse
import com.thanglong.project.usecase.Service.AttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("attributes") // Giữ lại base URL nhất quán
@RequiredArgsConstructor
public class AttributeController {

    private final AttributeService attributeService;

    @PostMapping
    public ApiResponse<AttributeModel> createAttribute(@RequestBody AttributeModel attributeModel) {
        AttributeModel createdAttribute = attributeService.createAttribute(attributeModel);
        return ApiResponse.<AttributeModel>builder()
                .message("Attribute created successfully")
                .data(createdAttribute)
                .build();
    }

    @GetMapping
    public ApiResponse<List<AttributeModel>> getAllAttributes() {
        List<AttributeModel> attributes = attributeService.getAllAttributes();
        return ApiResponse.<List<AttributeModel>>builder()
                .message("Successfully retrieved all attributes")
                .data(attributes)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<AttributeModel> getAttributeById(@PathVariable Integer id) {
        AttributeModel attribute = attributeService.getAttributeById(id);
        return ApiResponse.<AttributeModel>builder()
                .message("Successfully retrieved attribute")
                .data(attribute)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<AttributeModel> updateAttribute(@PathVariable Integer id, @RequestBody AttributeModel attributeDetails) {
        AttributeModel updatedAttribute = attributeService.updateAttribute(id, attributeDetails);
        return ApiResponse.<AttributeModel>builder()
                .message("Attribute updated successfully")
                .data(updatedAttribute)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteAttribute(@PathVariable Integer id) {
        attributeService.deleteAttribute(id);
        return ApiResponse.<Void>builder()
                .message("Attribute deleted successfully")
                .build();
    }
}
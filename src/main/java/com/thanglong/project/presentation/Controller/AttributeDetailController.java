package com.thanglong.project.presentation.Controller;

import com.thanglong.project.domain.model.AttributeDetailModel;
import com.thanglong.project.usecase.DTO.Response.ApiResponse;
import com.thanglong.project.usecase.Service.AttributeDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("attribute-details")
@RequiredArgsConstructor
public class AttributeDetailController {

    private final AttributeDetailService attributeDetailService;

    @PostMapping
    public ApiResponse<AttributeDetailModel> createAttributeDetail(@RequestBody AttributeDetailModel detailModel) {
        AttributeDetailModel createdDetail = attributeDetailService.createAttributeDetail(detailModel);
        return ApiResponse.<AttributeDetailModel>builder()
                .message("Attribute detail created successfully")
                .data(createdDetail)
                .build();
    }

    @GetMapping
    public ApiResponse<List<AttributeDetailModel>> getAllAttributeDetails() {
        List<AttributeDetailModel> details = attributeDetailService.getAllAttributeDetails();
        return ApiResponse.<List<AttributeDetailModel>>builder()
                .message("Successfully retrieved all attribute details")
                .data(details)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<AttributeDetailModel> getAttributeDetailById(@PathVariable Integer id) {
        AttributeDetailModel detail = attributeDetailService.getAttributeDetailById(id);
        return ApiResponse.<AttributeDetailModel>builder()
                .message("Successfully retrieved attribute detail")
                .data(detail)
                .build();
    }

    @GetMapping("/attribute/{attributeId}")
    public ApiResponse<List<AttributeDetailModel>> getDetailsByAttributeId(@PathVariable Integer attributeId) {
        List<AttributeDetailModel> details = attributeDetailService.getDetailsByAttributeId(attributeId);
        return ApiResponse.<List<AttributeDetailModel>>builder()
                .message("Successfully retrieved details for attribute")
                .data(details)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<AttributeDetailModel> updateAttributeDetail(@PathVariable Integer id, @RequestBody AttributeDetailModel detailModel) {
        AttributeDetailModel updatedDetail = attributeDetailService.updateAttributeDetail(id, detailModel);
        return ApiResponse.<AttributeDetailModel>builder()
                .message("Attribute detail updated successfully")
                .data(updatedDetail)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteAttributeDetail(@PathVariable Integer id) {
        attributeDetailService.deleteAttributeDetail(id);
        return ApiResponse.<Void>builder()
                .message("Attribute detail deleted successfully")
                .build();
    }
}
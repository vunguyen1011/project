package com.thanglong.project.usecase.Service;

import com.thanglong.project.domain.ENUM.ErrorCode;
import com.thanglong.project.domain.ENUM.InputType;
import com.thanglong.project.domain.model.AttributeDetailModel;
import com.thanglong.project.domain.model.AttributeModel;
import com.thanglong.project.domain.model.ProductVariantModel;
import com.thanglong.project.domain.repository.AttributeDetailRepository;
import com.thanglong.project.domain.repository.AttributeRepository;
import com.thanglong.project.domain.repository.ProductRepository;
import com.thanglong.project.domain.repository.ProductVariantRepository;
import com.thanglong.project.usecase.DTO.Request.CreateVariantProduct;
import com.thanglong.project.usecase.Exception.WebErrorConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductVariantService {

    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository;
    private final AttributeRepository attributeRepository;
    private final AttributeDetailRepository attributeDetailRepository;
    private final ImageService imageService;

    @Transactional
    public ProductVariantModel createProductVariant(CreateVariantProduct request) {
        validateParentProduct(request.getProductId());
        List<AttributeDetailModel> processedAttributes = processAttributes(request.getAttributes());

        ProductVariantModel newVariantModel = ProductVariantModel.builder()
                .productId(request.getProductId())
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .costPrice(request.getCostPrice())
                .sellPrice(request.getSellPrice())
                .attributes(processedAttributes)
                .build();

        ProductVariantModel savedVariant = productVariantRepository.save(newVariantModel);

        if (request.getImages() != null && !request.getImages().isEmpty()) {
            try {
                List<byte[]> fileDataList = convertMultipartFilesToByteArrays(request.getImages());
                imageService.uploadMultipleImagesForProduct(fileDataList, savedVariant.getId());
            } catch (IOException e) {
                throw new WebErrorConfig(ErrorCode.FILE_READ_ERROR);
            }
        }

        return savedVariant;
    }

    private void validateParentProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new WebErrorConfig(ErrorCode.PRODUCT_NOT_FOUND);
        }
    }

    private List<byte[]> convertMultipartFilesToByteArrays(List<MultipartFile> files) throws IOException {
        List<byte[]> dataList = new ArrayList<>();
        for (MultipartFile file : files) {
            dataList.add(file.getBytes());
        }
        return dataList;
    }

    private List<AttributeDetailModel> processAttributes(List<AttributeDetailModel> attributesFromRequest) {
        if (attributesFromRequest == null || attributesFromRequest.isEmpty()) {
            return new ArrayList<>();
        }
        return attributesFromRequest.stream()
                .map(this::findOrCreateAttributeDetail)
                .collect(Collectors.toList());
    }

    private AttributeDetailModel findOrCreateAttributeDetail(AttributeDetailModel detailRequest) {
        AttributeModel parentAttribute = attributeRepository.findById(detailRequest.getAttributeId())
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.ATTRIBUTE_NOT_FOUND));

        if (parentAttribute.getInputType() == InputType.DROPDOWN) {
            if (detailRequest.getId() == null) {
                throw new WebErrorConfig(ErrorCode.ATTRIBUTE_DETAIL_ID_REQUIRED);
            }
            return attributeDetailRepository.findById(detailRequest.getId())
                    .orElseThrow(() -> new WebErrorConfig(ErrorCode.ATTRIBUTE_DETAIL_NOT_FOUND));

        } else if (parentAttribute.getInputType() == InputType.TEXT_INPUT) {
            if (detailRequest.getValue() == null) {
                throw new WebErrorConfig(ErrorCode.ATTRIBUTE_DETAIL_VALUE_REQUIRED);
            }
            return attributeDetailRepository
                    .findByAttributeIdAndValue(parentAttribute.getId(), detailRequest.getValue())
                    .orElseGet(() -> {
                        AttributeDetailModel newDetail = new AttributeDetailModel();
                        newDetail.setAttributeId(parentAttribute.getId());
                        newDetail.setValue(detailRequest.getValue());
                        return attributeDetailRepository.save(newDetail);
                    });
        }

        throw new IllegalArgumentException("Unsupported InputType: " + parentAttribute.getInputType());
    }
}
package com.thanglong.project.usecase.Service;

import com.thanglong.project.domain.ENUM.ErrorCode;
import com.thanglong.project.domain.model.AttributeModel;
import com.thanglong.project.domain.repository.AttributeRepository;
import com.thanglong.project.domain.repository.CategoryRepository;
import com.thanglong.project.usecase.Exception.WebErrorConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttributeService {

    private final AttributeRepository attributeRepository;
    private final CategoryRepository categoryRepository;

    public AttributeModel createAttribute(AttributeModel attributeModel) {
        checkIfCategoryExists(attributeModel.getCategoryId());
        attributeRepository.findByName(attributeModel.getName()).ifPresent(attr -> {
            throw new WebErrorConfig(ErrorCode.ATTRIBUTE_ALREADY_EXITED);
        });
        var saveAttribute= attributeRepository.save(attributeModel);
        saveAttribute.setCategoryId(attributeModel.getCategoryId());
        return  saveAttribute;
    }

    public List<AttributeModel> getAllAttributes() {
        return attributeRepository.findAll();
    }

    public AttributeModel getAttributeById(Integer id) {
        return attributeRepository.findById(id)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.ATTRIBUTE_NOT_FOUND));
    }

    public AttributeModel updateAttribute(Integer id, AttributeModel attributeDetails) {
        checkIfCategoryExists(attributeDetails.getCategoryId());
        AttributeModel existingAttribute = getAttributeById(id);
        attributeRepository.findByName(attributeDetails.getName()).ifPresent(attr -> {
            if (!attr.getId().equals(id)) {
                throw new WebErrorConfig(ErrorCode.ATTRIBUTE_ALREADY_EXITED);
            }
        });
        existingAttribute.setName(attributeDetails.getName());
        existingAttribute.setCategoryId(attributeDetails.getCategoryId()); // Cập nhật categoryId
        return attributeRepository.save(existingAttribute);
    }

    public void deleteAttribute(Integer id) {
        // Dùng getAttributeById để đảm bảo thuộc tính tồn tại trước khi xóa
        AttributeModel attributeToDelete = getAttributeById(id);
        attributeRepository.deleteById(attributeToDelete.getId());
    }


    private void checkIfCategoryExists(Integer categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new WebErrorConfig(ErrorCode.CATEGORY_NOT_FOUND);
        }
    }
}
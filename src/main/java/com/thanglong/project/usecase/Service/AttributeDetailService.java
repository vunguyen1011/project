package com.thanglong.project.usecase.Service;

import com.thanglong.project.domain.ENUM.ErrorCode;
import com.thanglong.project.domain.model.AttributeDetailModel;
import com.thanglong.project.domain.repository.AttributeDetailRepository;
import com.thanglong.project.domain.repository.AttributeRepository; // Cần để kiểm tra Attribute cha
import com.thanglong.project.usecase.Exception.WebErrorConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttributeDetailService {
    private final AttributeDetailRepository attributeDetailRepository;
    private final AttributeRepository attributeRepository; // Dependency để kiểm tra Attribute cha
    public AttributeDetailModel createAttributeDetail(AttributeDetailModel detailModel) {
        checkIfAttributeExists(detailModel.getAttributeId());
        if (attributeDetailRepository.existsByAttributeIdAndValue(detailModel.getAttributeId(), detailModel.getValue())) {
            throw new WebErrorConfig(ErrorCode.ATTRIBUTE_DETAIL_ALREADY_EXITED);
        }

        return attributeDetailRepository.save(detailModel);
    }
    public List<AttributeDetailModel> getAllAttributeDetails() {
        return attributeDetailRepository.findAll();
    }
    public List<AttributeDetailModel> getDetailsByAttributeId(Integer attributeId) {
        checkIfAttributeExists(attributeId);
        return attributeDetailRepository.findByAttributeId(attributeId);
    }
    public AttributeDetailModel getAttributeDetailById(Integer id) {
        return attributeDetailRepository.findById(id)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.ATTRIBUTE_DETAIL_NOT_FOUND));
    }

    public AttributeDetailModel updateAttributeDetail(Integer id, AttributeDetailModel detailModel) {
        checkIfAttributeExists(detailModel.getAttributeId());
        AttributeDetailModel existingDetail = getAttributeDetailById(id);
        if (!existingDetail.getValue().equals(detailModel.getValue())) {
            if (attributeDetailRepository.existsByAttributeIdAndValue(detailModel.getAttributeId(), detailModel.getValue())) {
                throw new WebErrorConfig(ErrorCode.ATTRIBUTE_DETAIL_ALREADY_EXITED);
            }
        }

        // 4. Cập nhật thông tin
        existingDetail.setValue(detailModel.getValue());
        existingDetail.setAttributeId(detailModel.getAttributeId());

        return attributeDetailRepository.save(existingDetail);
    }

    public void deleteAttributeDetail(Integer id) {
        // Đảm bảo đối tượng tồn tại trước khi xóa
        AttributeDetailModel detailToDelete = getAttributeDetailById(id);
        attributeDetailRepository.deleteById(detailToDelete.getId());
    }


    private void checkIfAttributeExists(Integer attributeId) {
        if (!attributeRepository.findById(attributeId).isPresent()) {
            throw new WebErrorConfig(ErrorCode.ATTRIBUTE_NOT_FOUND);
        }
    }
}
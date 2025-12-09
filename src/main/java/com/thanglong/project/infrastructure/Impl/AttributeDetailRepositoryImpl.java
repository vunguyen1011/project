package com.thanglong.project.infrastructure.Impl;

import com.thanglong.project.domain.ENUM.AttributeType;
import com.thanglong.project.domain.ENUM.ErrorCode;
import com.thanglong.project.domain.model.AttributeDetailModel;
import com.thanglong.project.domain.repository.AttributeDetailRepository;
import com.thanglong.project.infrastructure.Entity.Attribute;
import com.thanglong.project.infrastructure.Entity.AttributeDetail;
import com.thanglong.project.infrastructure.repository.AttributeDetailJpaRepository;
import com.thanglong.project.infrastructure.repository.AttributeJpaRepository;
import com.thanglong.project.usecase.Exception.WebErrorConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AttributeDetailRepositoryImpl implements AttributeDetailRepository {

    private final AttributeDetailJpaRepository jpaRepository;
    private final AttributeJpaRepository attributeJpaRepository;

    @Override
    public AttributeDetailModel save(AttributeDetailModel model) {
        Attribute attribute = attributeJpaRepository.findById(model.getAttributeId())
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.ATTRIBUTE_NOT_FOUND));

        AttributeDetail entity = model.getId() != null ?
                jpaRepository.findById(model.getId()).orElseGet(AttributeDetail::new) :
                new AttributeDetail();

        mapModelToEntity(model, entity, attribute);

        AttributeDetail savedEntity = jpaRepository.save(entity);
        return toModel(savedEntity);
    }

    @Override
    public Optional<AttributeDetailModel> findById(Integer id) {
        return jpaRepository.findById(id).map(this::toModel);
    }

    @Override
    public List<AttributeDetailModel> findAll() {
        return jpaRepository.findAll().stream().map(this::toModel).collect(Collectors.toList());
    }

    @Override
    public List<AttributeDetailModel> findByAttributeId(Integer attributeId) {
        return jpaRepository.findByAttributeId(attributeId).stream().map(this::toModel).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByAttributeIdAndValue(Integer attributeId, Object value) {
        Attribute attribute = attributeJpaRepository.findById(attributeId)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.ATTRIBUTE_NOT_FOUND));
        AttributeType type = attribute.getType();
        switch (type) {
            case TEXT:
                return jpaRepository.existsByAttributeIdAndTextValue(attributeId, (String) value);
            case NUMBER:
                Long numberVal = (value instanceof Number)
                        ? ((Number) value).longValue()
                        : Long.parseLong(value.toString());
                return jpaRepository.existsByAttributeIdAndNumberValue(attributeId, numberVal);
            case DECIMAL:
                BigDecimal decimalVal = (value instanceof BigDecimal)
                        ? (BigDecimal) value
                        : new BigDecimal(value.toString());
                return jpaRepository.existsByAttributeIdAndDecimalValue(attributeId, decimalVal);
            case BOOLEAN:
                Boolean boolVal = (value instanceof Boolean)
                        ? (Boolean) value
                        : Boolean.parseBoolean(value.toString());
                return jpaRepository.existsByAttributeIdAndBooleanValue(attributeId, boolVal);
            case DATE:
                LocalDate dateVal = (value instanceof LocalDate)
                        ? (LocalDate) value
                        : LocalDate.parse(value.toString());
                return jpaRepository.existsByAttributeIdAndDateValue(attributeId, dateVal);
            case DATETIME:
                LocalDateTime dateTimeVal = (value instanceof LocalDateTime)
                        ? (LocalDateTime) value
                        : LocalDateTime.parse(value.toString());
                return jpaRepository.existsByAttributeIdAndDatetimeValue(attributeId, dateTimeVal);
            default:
                throw new IllegalArgumentException("Unsupported AttributeType: " + type);
        }
    }


    private void mapModelToEntity(AttributeDetailModel model, AttributeDetail entity, Attribute attribute) {
        entity.setAttributeId(attribute.getId());

        entity.setTextValue(null);
        entity.setNumberValue(null);
        entity.setDecimalValue(null);
        entity.setBooleanValue(null);
        entity.setDateValue(null);
        entity.setDatetimeValue(null);

        switch (attribute.getType()) {
            case TEXT:
                entity.setTextValue((String) model.getValue());
                break;
            case NUMBER:
                entity.setNumberValue(((Number) model.getValue()).longValue());
                break;
            case DECIMAL:
                entity.setDecimalValue(new BigDecimal(model.getValue().toString()));
                break;
            case BOOLEAN:
                entity.setBooleanValue((Boolean) model.getValue());
                break;
            case DATE:
                entity.setDateValue((LocalDate) model.getValue());
                break;
            case DATETIME:
                entity.setDatetimeValue((LocalDateTime) model.getValue());
                break;
            default:
                throw new IllegalArgumentException("Unsupported DataType: " + attribute.getType());
        }
    }

    private AttributeDetailModel toModel(AttributeDetail entity) {
        AttributeDetailModel model = new AttributeDetailModel();
        model.setId(entity.getId());
        model.setAttributeId(entity.getAttributeId());


        if (entity.getTextValue() != null) {
            model.setValue(entity.getTextValue());
        } else if (entity.getNumberValue() != null) {
            model.setValue(entity.getNumberValue());
        } else if (entity.getDecimalValue() != null) {
            model.setValue(entity.getDecimalValue());
        } else if (entity.getBooleanValue() != null) {
            model.setValue(entity.getBooleanValue());
        } else if (entity.getDateValue() != null) {
            model.setValue(entity.getDateValue());
        } else if (entity.getDatetimeValue() != null) {
            model.setValue(entity.getDatetimeValue());
        }

        return model;
    }

    @Override
    public Optional<AttributeDetailModel> findByAttributeIdAndValue(Integer attributeId, Object value) {
        return Optional.empty();
    }
}
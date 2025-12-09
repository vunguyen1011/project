package com.thanglong.project.infrastructure.repository;

import com.thanglong.project.infrastructure.Entity.AttributeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AttributeDetailJpaRepository extends JpaRepository<AttributeDetail, Integer> {
    List<AttributeDetail> findByAttributeId(Integer attributeId);
    boolean existsByAttributeIdAndTextValue(Integer attributeId, String textValue);
    boolean existsByAttributeIdAndNumberValue(Integer attributeId, Long numberValue);
    boolean existsByAttributeIdAndDecimalValue(Integer attributeId, BigDecimal decimalValue);
    boolean existsByAttributeIdAndBooleanValue(Integer attributeId, Boolean booleanValue);
    boolean existsByAttributeIdAndDateValue(Integer attributeId, LocalDate dateValue);
    boolean existsByAttributeIdAndDatetimeValue(Integer attributeId, LocalDateTime datetimeValue);

}
package com.thanglong.project.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributeDetailModel {
    private Integer id;
    private Integer attributeId;
    private Object value;
}
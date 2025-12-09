package com.thanglong.project.domain.model;

import com.thanglong.project.domain.ENUM.AttributeType;
import com.thanglong.project.domain.ENUM.InputType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttributeModel {
    private Integer id;
    private String name;
    private  Integer categoryId;
    private AttributeType type;
    private InputType inputType;


}

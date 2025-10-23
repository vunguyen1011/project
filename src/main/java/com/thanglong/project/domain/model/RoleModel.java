package com.thanglong.project.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder

public class RoleModel {
    private Integer id;
    private String name;

}

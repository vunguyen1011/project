package com.thanglong.project.infrastructure.Entity;

import com.thanglong.project.domain.ENUM.AttributeType;
import com.thanglong.project.domain.ENUM.InputType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
@Entity
@Data
@Table(name = "attributes")
@NoArgsConstructor
@AllArgsConstructor
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    private Integer categoryId;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private AttributeType type;
    @Enumerated(EnumType.STRING)
    @Column(name = "input_type", nullable = false)
    private InputType inputType; // <-- CỘT QUYẾT ĐỊNH MỌI THỨ


}

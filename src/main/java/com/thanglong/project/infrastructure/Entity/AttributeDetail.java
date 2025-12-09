package com.thanglong.project.infrastructure.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attribute_detail")
public class AttributeDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // GIỮ NGUYÊN: Dùng Integer để lưu ID của Attribute cha
    @Column(name = "attribute_id", nullable = false)
    private Integer attributeId;

    // CÁC CỘT GIÁ TRỊ VẪN GIỮ NGUYÊN
    @Column(name = "text_value")
    private String textValue;

    @Column(name = "number_value")
    private Long numberValue;

    @Column(name = "decimal_value")
    private BigDecimal decimalValue;

    @Column(name = "boolean_value")
    private Boolean booleanValue;

    @Column(name = "date_value")
    private LocalDate dateValue;

    @Column(name = "datetime_value")
    private LocalDateTime datetimeValue;

    // Giữ lại mối quan hệ ManyToMany với ProductVariant
    @ManyToMany(mappedBy = "attributes")
    @ToString.Exclude
    private List<ProductVariant> productVariants = new ArrayList<>();
}
package com.thanglong.project.infrastructure.Entity;

import com.thanglong.project.domain.ENUM.AttributeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
@Entity
@Data
@Table(name = "atutributes")
@NoArgsConstructor
@AllArgsConstructor
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING) // Lưu enum dưới dạng chuỗi trong DB
    @Column(name = "type_value", nullable = false)
    private AttributeType typeValue;

    // Hoàn thiện quan hệ hai chiều với AttributeDetailJpaEntity
    @OneToMany(

            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @ToString.Exclude
    private List<AttributeDetail> details = new ArrayList<>();

}

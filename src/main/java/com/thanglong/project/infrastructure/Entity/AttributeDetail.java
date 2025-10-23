package com.thanglong.project.infrastructure.Entity;

import jakarta.persistence.*;
import lombok.*; // Sử dụng các annotation cụ thể

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attribute_detail") // Thêm @Table cho rõ ràng
public class AttributeDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attribute_id")
    private Long attributeId;

    @Column(nullable = false)
    private String value;

    @ManyToMany(mappedBy = "attributes")
    @ToString.Exclude // SỬA: Ngăn lặp vô hạn trong toString()
    private List<ProductVariant> productVariants = new ArrayList<>();

    // SỬA: Override equals() và hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttributeDetail that = (AttributeDetail) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
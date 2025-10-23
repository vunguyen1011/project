package com.thanglong.project.infrastructure.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data

@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   private Long orderId;

    @Column(name = "product_detail", nullable = false, columnDefinition = "TEXT")
    private String productDetail; // Dùng kiểu TEXT để lưu chuỗi dài (JSON)

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
}

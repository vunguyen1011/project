package com.thanglong.project.infrastructure.Entity;

import jakarta.persistence.*;
import lombok.*; // Sử dụng các annotation cụ thể

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter // An toàn hơn khi dùng @Getter và @Setter riêng
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_variant")
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(nullable = false)
    private String name;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    private int quantity;

    @Column(name = "cost_price", precision = 10, scale = 2)
    private BigDecimal costPrice;

    @Column(name = "sell_price", precision = 10, scale = 2)
    private BigDecimal sellPrice;

    @Column(name = "quantity_sold")
    private Long quantitySold;

    @Column(name = "sku", length = 50, unique = true)
    private String sku;

    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude // SỬA: Ngăn lặp vô hạn trong toString()
    private List<Image> images = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "product_variant_attrib",
            joinColumns = @JoinColumn(name = "product_variant_id"),
            inverseJoinColumns = @JoinColumn(name = "attribute_detail_id")
    )
    @ToString.Exclude // SỬA: Ngăn lặp vô hạn trong toString()
    private List<AttributeDetail> attributes = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "voucher_product_variant",
            joinColumns = @JoinColumn(name = "product_variant_id"),
            inverseJoinColumns = @JoinColumn(name = "voucher_id")
    )
    @ToString.Exclude // SỬA: Ngăn lặp vô hạn trong toString()
    private List<Voucher> vouchers = new ArrayList<>();

    // SỬA: Override equals() và hashCode()
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ProductVariant that = (ProductVariant) o;
//        return id != null && Objects.equals(id, that.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }
}
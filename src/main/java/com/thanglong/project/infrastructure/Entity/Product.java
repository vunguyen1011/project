package com.thanglong.project.infrastructure.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products") // Đặt tên bảng rõ ràng
public class Product { // Đổi tên để phân biệt với Domain Entity

    @Id // Đánh dấu đây là khóa chính
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID sẽ được tự động tạo và tăng
    private Long id;

    @Column(name = "name", nullable = false, length = 255) // Cột không được null, giới hạn độ dài
    private String name;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "image_url") // Đặt tên cột rõ ràng hơn
    private String image;

    @Column(name = "average_rating")
    private Float averageRating;

    @Column(name = "total_sold")
    private Long totalSold;

    @Lob // Thích hợp cho các trường văn bản dài (TEXT hoặc CLOB trong DB)
    @Column(name = "description")
    private String description;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "brand_id")
    private Integer brandId; // Giả sử chỉ lưu ID, nếu muốn quan hệ thì dùng @ManyToOne

    @Column(name = "category_id")
    private Integer categoryId; // Tương tự brandId
    @Column(name="price_display")

    // Định nghĩa quan hệ một-nhiều với ProductVariant
    @OneToMany(

            cascade = CascadeType.ALL, // Các thay đổi (thêm, sửa, xóa) trên Product sẽ áp dụng cho các Variant liên quan
            orphanRemoval = true // Xóa các Variant không còn được Product tham chiếu đến
    )
    private List<ProductVariant> variants = new ArrayList<>();

    // === Helper methods để quản lý quan hệ hai chiều ===
    public void addVariant(ProductVariant variant) {
        this.variants.add(variant);
        variant.setProductId(this.getId());
    }

    public void removeVariant(ProductVariant variant) {
        this.variants.remove(variant);
        variant.setProductId(null);
    }
}
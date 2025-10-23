package com.thanglong.project.infrastructure.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "voucher")
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    private String description;
//
//    @Enumerated(EnumType.STRING) // Lưu Enum dưới dạng chuỗi trong DB (ví dụ: "PERCENTAGE")
//    @Column(name = "discount_type", nullable = false)
//    private DiscountType discountType;

    @Column(name = "discount_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountValue;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @ManyToMany(mappedBy = "vouchers") // "vouchers" là tên trường List trong ProductVariantJpaEntity
    @ToString.Exclude // Tránh lỗi lặp vô hạn khi gọi toString()
    private List<ProductVariant> productVariants = new ArrayList<>();

    // Override equals và hashCode để hoạt động đúng trong các Collection
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        VoucherJpaEntity that = (VoucherJpaEntity) o;
//        return id != null && Objects.equals(id, that.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }
}

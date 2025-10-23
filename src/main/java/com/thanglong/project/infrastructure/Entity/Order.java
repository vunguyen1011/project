package com.thanglong.project.infrastructure.Entity;


import com.thanglong.project.domain.ENUM.Order_Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data // Tự động tạo Getter, Setter, toString(), equals(), hashCode()
@NoArgsConstructor // Tự động tạo constructor không tham số
@AllArgsConstructor // Tự động tạo constructor với tất cả tham số
@Entity
// "ORDER" là một từ khóa trong SQL, vì vậy nên đặt tên bảng là "orders" để tránh lỗi
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;

    @Column(name = "total_price", nullable = false, precision = 19, scale = 4)
    private BigDecimal totalPrice;

    @Column(name = "create_Date", nullable = false)
    private LocalDateTime createDate;


    @Column(name = "product_detail", columnDefinition = "TEXT")
    private String productDetail;
    private Order_Status status;
    private Integer paymentMethodId;
    private String  voucherCode;

    @Column(name = "discount_amount", precision = 19, scale = 4)
    private BigDecimal discountAmount;
}

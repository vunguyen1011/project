package com.thanglong.project.infrastructure.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId; // Chỉ lưu ID để giữ cho model đơn giản
    private String city;
    private String province;
    private String ward;
    private String fullname;
    private String phone;
    private String note;
    private boolean isDefault;


}
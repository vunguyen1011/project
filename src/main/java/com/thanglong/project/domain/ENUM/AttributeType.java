package com.thanglong.project.domain.ENUM;

public enum AttributeType {
    // Kiểu cơ bản
    TEXT,        // Chuỗi ngắn hoặc dài (ví dụ: màu sắc, mô tả)
    NUMBER,      // Số nguyên (Integer)
    DECIMAL,     // Số thực hoặc giá trị tiền tệ (BigDecimal)
    BOOLEAN,     // Giá trị đúng/sai
    DATE,        // Ngày (yyyy-MM-dd)
    DATETIME,    // Ngày giờ (yyyy-MM-dd HH:mm:ss)
    JSON         // Dữ liệu dạng JSON (tùy chọn đặc biệt)
}
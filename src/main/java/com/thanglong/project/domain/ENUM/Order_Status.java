package com.thanglong.project.domain.ENUM;

public enum Order_Status {
    PENDING("Chờ xác nhận", 1),
    PROCESSING("Chờ giao hàng", 2),
    SHIPPED("Đã giao hàng", 3),
    DELIVERED("Hoàn thành", 4),
    CANCELLED("Đã hủy", 2);

    private final String description;
    private final int level;

    Order_Status(String description, int level) {
        this.description = description;
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public int getLevel() {
        return level;
    }
}

package com.thanglong.project.domain.ENUM;

public enum ErrorCode {
    USER_NOT_FOUND(404, "Không tìm thấy người dùng"),
    ROLE_NOT_FOUND(1000, "Vai trò không tồn tại"),
    PROVIDER_NOT_FOUND(1001, "Provider not found"),
    OTP_NOT_CORRECT(1002, "Mã Otp không đúng"),
    PROVIDER_NOT_SUPPORTED(1003,"Không hỗ trợ đăng nhập"),
    ATTRIBUTE_DETAIL_NOT_FOUND(1000,"Chi tiết thuộc tính không tồn tại"),
    ATTRIBUTE_DETAIL_ALREADY_EXITED(1001,"Chi tiết thuộc tính đã tồn tại"),
    ATTRIBUTE_NOT_FOUND(2001,"Thuộc tính không tồn tại"),
    ATTRIBUTE_ALREADY_EXITED(2002,"Thuộc tính đã tồn tại"),
    PRODUCT_NOT_FOUND(1004,"Sản phẩm không tồn tại"),
    PRODUCT_ALREADY_EXITED(1005,"Sản phẩm đã tồn tại"),
    USER_ALREADY_EXISTS(1009, "Người dùng đã tồn tại"),
    BRAND_ALREADY_EXITED(1005, "Nhãn hàng đã tồn tại"),
    BRAND_NOT_FOUND(1006, "Không tìm thấy nhãn hàng"),
    PRODUCT_VARIANT_NOT_FOUND(1010, "Không tìm thấy biến thể sản phẩm"),
    CATEGORY_ALREADY_EXITED(1007, "Danh mục đã tồn tại"),
    CATEGORY_NOT_FOUND(1008, "Không tìm thấy danh mục"),
    ATTRIBUTE_DETAIL_ID_REQUIRED(1012, "Yêu cầu ID chi tiết thuộc tính"),
    ATTRIBUTE_DETAIL_VALUE_REQUIRED(1013, "Yêu cầu giá trị chi tiết thuộc tính"),
    FILE_READ_ERROR(1014, "Lỗi đọc file");



    private final int code;
    private final String message;
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }
}

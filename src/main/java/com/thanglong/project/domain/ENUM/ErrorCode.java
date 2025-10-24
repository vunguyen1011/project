package com.thanglong.project.domain.ENUM;

public enum ErrorCode {
    USER_NOT_FOUND(404, "User not found"),
    ROLE_NOT_FOUND(1000, "Role not found"),
    PROVIDER_NOT_FOUND(1001, "Provider not found"),
    OTP_NOT_CORRECT(1002, "Mã Otp không đúng"),
    PROVIDER_NOT_SUPPORTED(1003,"Không hỗ trợ đăng nhập"),
    USER_ALREADY_EXISTS(409, "User already exists"),
    BRAND_ALREADY_EXITED(501, "Brand already exits"),
    BRAND_NOT_FOUND(502, "Brand not found"),
    CATEGORY_ALREADY_EXITED(503, "Category already exits"),
    CATEGORY_NOT_FOUND(504, "Category not found");


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

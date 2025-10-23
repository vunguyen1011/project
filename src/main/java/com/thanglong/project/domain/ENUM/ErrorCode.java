package com.thanglong.project.domain.ENUM;

public enum ErrorCode {
    USER_NOT_FOUND(404, "User not found"),
    ROLE_NOT_FOUND(1000,"Role not found"),
    PROVIDER_NOT_FOUND(1001,"Provider not found"),
    OTP_NOT_CORRECT(1002,"Mã Otp không đúng"),
    USER_ALREADY_EXISTS(409, "User already exists");


    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
    private final int code;
    private final String message;
    public int getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }
}

package com.thanglong.project.usecase.DTO.Request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}

package com.thanglong.project.usecase.DTO.Request;

import lombok.Data;

@Data

public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean gender;


}

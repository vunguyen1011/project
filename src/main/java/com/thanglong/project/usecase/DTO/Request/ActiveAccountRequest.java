package com.thanglong.project.usecase.DTO.Request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ActiveAccountRequest {
    private String email;
    private String otp;

}

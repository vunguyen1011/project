package com.thanglong.project.usecase.DTO.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {
    private int code;
     private T data;
        private String message;
}

package com.thanglong.project.presentation.Exception;

import com.thanglong.project.domain.ENUM.ErrorCode;
import com.thanglong.project.usecase.DTO.Response.ApiResponse;
import com.thanglong.project.usecase.Exception.WebErrorConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = WebErrorConfig.class)
    ResponseEntity<ApiResponse> handlerRunTimeException(WebErrorConfig exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return ResponseEntity.badRequest()
                .body(ApiResponse.builder().code(errorCode.getCode()).message(errorCode.getMessage())
                        .build()
                );
    }
}

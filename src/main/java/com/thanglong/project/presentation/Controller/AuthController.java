package com.thanglong.project.presentation.Controller;

import com.thanglong.project.domain.model.UserModel;
import com.thanglong.project.usecase.DTO.Request.LoginRequest;
import com.thanglong.project.usecase.DTO.Response.ApiResponse;
import com.thanglong.project.usecase.DTO.Request.ActiveAccountRequest;
import com.thanglong.project.usecase.DTO.Request.RegisterRequest;
import com.thanglong.project.usecase.DTO.Response.TokenResponse;
import com.thanglong.project.usecase.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auths")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/user")
    public ApiResponse<UserModel> register(@RequestBody RegisterRequest request){
        UserModel userModel = authService.register(request);
        return ApiResponse.<UserModel>builder()
                .code(200)
                .data(userModel)
                .message("Register successfully")
                .build();
    }
    @PostMapping("/active-account")
    ApiResponse<Void> activeAccount(@RequestBody ActiveAccountRequest request){
        authService.activeAccount(request);
        return ApiResponse.<Void>builder()
                .message("active account successfully")
                .build();

    }
    @PostMapping("/login")
    ApiResponse<TokenResponse> login(@RequestBody LoginRequest request){
        return ApiResponse.<TokenResponse>builder()
                .data(authService.authenticate(request))
                .build();
    }

}

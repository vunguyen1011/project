package com.thanglong.project.presentation.Controller;

import com.thanglong.project.domain.model.UserModel;
import com.thanglong.project.usecase.DTO.Response.ApiResponse;
import com.thanglong.project.usecase.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @GetMapping
    public ApiResponse<List<UserModel>> getAll(){
        return ApiResponse.<List<UserModel>>builder()
                .message("All users")
                .data(userService.getAll())
                .build();
    }
}

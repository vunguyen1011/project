package com.thanglong.project.presentation.Controller;


import com.thanglong.project.usecase.DTO.Response.ApiResponse;
import com.thanglong.project.usecase.Service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/mails")
public class MailController {
    private final EmailService emailService;

    @GetMapping
    ApiResponse<Void> sendEmail(@RequestParam String to) {
        emailService.sendEmail(to);
        return ApiResponse.<Void>builder()
                .build();
    }
}

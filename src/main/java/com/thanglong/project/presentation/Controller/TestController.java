package com.thanglong.project.presentation.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class TestController {
    @GetMapping("/success")
    public String success(@RequestParam String accessToken,
                          @RequestParam String refreshToken) {
        return "Login thành công! AccessToken: " + accessToken + "<br>RefreshToken: " + refreshToken;
    }
}

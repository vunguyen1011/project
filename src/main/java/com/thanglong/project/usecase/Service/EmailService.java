package com.thanglong.project.usecase.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class EmailService {
    private static final SecureRandom random = new SecureRandom();
    private final JavaMailSender javaMailSender;
    private final RedisService redisService;
    @Value("${spring.mail.from}")
    private String from;

    private String generateNumericOtp() {
        int number = random.nextInt(1000000);
        return String.format("%06d", number);
    }
    @Async
    public void sendEmail(String to){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject("Xác nhận tài khoản của bạn");
        String otp=generateNumericOtp();

        simpleMailMessage.setText("Your otp is "+otp +"\n"+"Mã na có hiệu lực trong vòng 5 phút");
        javaMailSender.send(simpleMailMessage);
        redisService.setValue(to,otp,300); // nên sử  dụng bất đồng bộ ở đây
    }

}

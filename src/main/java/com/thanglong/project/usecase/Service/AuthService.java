package com.thanglong.project.usecase.Service;

import com.thanglong.project.domain.ENUM.ErrorCode;
import com.thanglong.project.domain.model.ProviderModel;
import com.thanglong.project.domain.model.RoleModel;
import com.thanglong.project.domain.model.UserModel;
import com.thanglong.project.domain.repository.ProviderRepository;
import com.thanglong.project.domain.repository.RoleRepository;
import com.thanglong.project.domain.repository.UserRepository;
import com.thanglong.project.infrastructure.Security.JwtProvider;
import com.thanglong.project.usecase.DTO.Request.ActiveAccountRequest;
import com.thanglong.project.usecase.DTO.Request.LoginRequest;
import com.thanglong.project.usecase.DTO.Request.RegisterRequest;
import com.thanglong.project.usecase.DTO.Response.TokenResponse;
import com.thanglong.project.usecase.Exception.WebErrorConfig;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.thanglong.project.usecase.service.MyUserDetailService;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProviderRepository providerRepository;
    private final EmailService emailService;
    private final RedisService redisService;
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailService userDetailService;
    private final JwtProvider jwtTokenProvider;
    @Transactional
    public UserModel register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new WebErrorConfig(ErrorCode.USER_ALREADY_EXISTS);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new WebErrorConfig(ErrorCode.USER_ALREADY_EXISTS);
        }
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        RoleModel defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.ROLE_NOT_FOUND));
        ProviderModel providerModel = providerRepository.findByName("LOCAL").orElseThrow(() -> new WebErrorConfig(ErrorCode.PROVIDER_NOT_FOUND));
        UserModel newUser = UserModel.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(hashedPassword)
                .gender(request.isGender())
                .phone(request.getPhone())
                .isActive(false)
                .roles(Set.of(defaultRole))
                .providers(Set.of(providerModel))
                .build();
        emailService.sendEmail(request.getEmail());// ở đây đã  được xử lý bất đồng bộ
        return userRepository.save(newUser);
    }

    public void activeAccount(ActiveAccountRequest request) {
        UserModel user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));

        String storedOtp = redisService.getValue(request.getEmail());
        System.out.println(request.getOtp());
        System.out.println("gui tu email "+storedOtp);
        if (storedOtp == null || !storedOtp.equals(request.getOtp())) {
            throw new WebErrorConfig(ErrorCode.OTP_NOT_CORRECT);
        }
        user.setActive(true);
        userRepository.save(user);
        redisService.deleteValue(request.getEmail());
    }
    public TokenResponse authenticate(LoginRequest loginRequest ) {


        try {
            // Tạo đối tượng xác thực từ thông tin đăng nhập
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            var userDetails = userDetailService.loadUserByUsername(loginRequest.getUsername());
            if(!userDetails.isEnabled()) throw  new WebErrorConfig(ErrorCode.USER_NOT_FOUND);
            String accessToken = jwtTokenProvider.generateAccessToken(userDetails.getUsername());
            String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails.getUsername());
            String tokenId = jwtTokenProvider.extractTokenId(refreshToken);
            redisService.addToken(tokenId, jwtTokenProvider.getRefreshExpiration());
           //  giới hạn thiết bị
            // giới hạn số lượng tài khoản đăng nhập cùng 1 lúc
            return TokenResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (AuthenticationException e) {
            throw new WebErrorConfig(ErrorCode.USER_NOT_FOUND);
        }
    }
}
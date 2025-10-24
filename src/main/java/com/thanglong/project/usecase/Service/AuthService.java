package com.thanglong.project.usecase.Service;

import com.thanglong.project.domain.ENUM.ErrorCode;
import com.thanglong.project.domain.model.ProviderModel;
import com.thanglong.project.domain.model.RoleModel;
import com.thanglong.project.domain.model.UserModel;
import com.thanglong.project.domain.repository.ProviderRepository;
import com.thanglong.project.domain.repository.RoleRepository;
import com.thanglong.project.domain.repository.UserRepository;
import com.thanglong.project.infrastructure.Security.JwtProvider;
import com.thanglong.project.infrastructure.Security.MyUserDetail;
import com.thanglong.project.usecase.DTO.Request.ActiveAccountRequest;
import com.thanglong.project.usecase.DTO.Request.LoginRequest;
import com.thanglong.project.usecase.DTO.Request.RegisterRequest;
import com.thanglong.project.usecase.DTO.Response.TokenResponse;
import com.thanglong.project.usecase.Exception.WebErrorConfig;
import com.thanglong.project.usecase.service.MyUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        RoleModel defaultRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new WebErrorConfig(ErrorCode.ROLE_NOT_FOUND));
        ProviderModel providerModel = providerRepository.findByName("LOCAL").orElseThrow(() -> new WebErrorConfig(ErrorCode.PROVIDER_NOT_FOUND));
        UserModel newUser = UserModel.builder().firstName(request.getFirstName()).lastName(request.getLastName()).email(request.getEmail()).username(request.getUsername()).password(hashedPassword).gender(request.isGender()).phone(request.getPhone()).isActive(false).roles(Set.of(defaultRole)).providers(Set.of(providerModel)).build();
        emailService.sendEmail(request.getEmail());// ở đây đã  được xử lý bất đồng bộ
        return userRepository.save(newUser);
    }

    public void activeAccount(ActiveAccountRequest request) {
        UserModel user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));
        String storedOtp = redisService.getValue(request.getEmail());
        System.out.println(request.getOtp());
        System.out.println("gui tu email " + storedOtp);
        if (storedOtp == null || !storedOtp.equals(request.getOtp())) {
            throw new WebErrorConfig(ErrorCode.OTP_NOT_CORRECT);
        }
        user.setActive(true);
        userRepository.save(user);
        redisService.deleteValue(request.getEmail());
    }

    public TokenResponse authenticate(LoginRequest loginRequest) {
        try {
            // Tạo đối tượng xác thực từ thông tin đăng nhập
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
           var userModel= userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(()-> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));
            var userDetails = MyUserDetail.from(userModel);
            if (!userDetails.isEnabled()) throw new WebErrorConfig(ErrorCode.USER_NOT_FOUND);
            String accessToken = jwtTokenProvider.generateAccessToken(userModel);
            String refreshToken = jwtTokenProvider.generateRefreshToken(userModel);
            String tokenId = jwtTokenProvider.extractTokenId(refreshToken);
            redisService.addToken(tokenId, jwtTokenProvider.getRefreshExpiration());
            //  giới hạn thiết bị
            // giới hạn số lượng tài khoản đăng nhập cùng 1 lúc
            return TokenResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
        } catch (AuthenticationException e) {
            throw new WebErrorConfig(ErrorCode.USER_NOT_FOUND);
        }
    }

    private String getEmailFromGoogle(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        return oAuth2AuthenticationToken.getPrincipal().getAttribute("email");
    }

    public UserModel handleOauth2WithGoogle(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        String email = getEmailFromGoogle(oAuth2AuthenticationToken);
        if (userRepository.existsByEmail(email)) {
            var userModel = userRepository.findByEmail(email).orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));
            boolean alreadyLinked = userModel.getProviders().stream()
                    .anyMatch(provider -> provider.getName().equals("GOOGLE"));

            if (!alreadyLinked) {
                var googleProvider = providerRepository.findByName("GOOGLE").orElseThrow(() -> new WebErrorConfig(ErrorCode.PROVIDER_NOT_FOUND));
                userModel.getProviders().add(googleProvider);
                userRepository.save(userModel);
            }
            if (!userModel.isActive()) {
                userModel.setActive(true);
            }
            userRepository.save(userModel);
            return userModel;
        } else {
            OAuth2User principal = oAuth2AuthenticationToken.getPrincipal();
            UserModel newUser = new UserModel();
            newUser.setEmail(email);
            String googleUserId = principal.getAttribute("sub").toString();
            newUser.setUsername(googleUserId);
            newUser.setFirstName(principal.getAttribute("given_name"));
            newUser.setLastName(principal.getAttribute("family_name"));
            newUser.setActive(true);   // Kích hoạt tài khoản ngay
            // Gán vai trò mặc định, ví dụ: "USER"
            var userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new WebErrorConfig(ErrorCode.ROLE_NOT_FOUND));
            newUser.setRoles(Set.of(userRole));
            // Gán nhà cung cấp là "GOOGLE"
            var googleProvider = providerRepository.findByName("GOOGLE").orElseThrow(() -> new WebErrorConfig(ErrorCode.PROVIDER_NOT_FOUND));
            newUser.setProviders(Set.of(googleProvider));
            return userRepository.save(newUser);
        }
    }

    @Transactional
    public TokenResponse loginWithOAuth2(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        String registrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        UserModel userModel;

        if ("google".equalsIgnoreCase(registrationId)) {
            userModel = handleOauth2WithGoogle(oAuth2AuthenticationToken);
        }
//        } else if ("github".equalsIgnoreCase(registrationId)) {
//            userModel = handleOauth2WithGitHub(oAuth2AuthenticationToken);

        // Thêm else if cho các provider khác ở đây (Facebook, etc.)
        else {
            throw new WebErrorConfig(ErrorCode.PROVIDER_NOT_SUPPORTED);
        }
        System.out.println("--- THÔNG TIN DEBUG ---");
        System.out.println("User tìm thấy bằng email: " + userModel.getEmail());
        System.out.println("Username đang được gửi đến JWT Provider: " + userModel.getUsername());
        System.out.println("User có active không?: " + userModel.isActive());
        System.out.println("--- KẾT THÚC DEBUG ---");
        String accessToken = jwtTokenProvider.generateAccessToken(userModel);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userModel);
        redisService.addToken(jwtTokenProvider.extractTokenId(refreshToken), jwtTokenProvider.getRefreshExpiration());
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
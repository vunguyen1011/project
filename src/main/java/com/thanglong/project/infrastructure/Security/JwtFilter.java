package com.thanglong.project.infrastructure.Security; // Sửa lại package cho đúng


import com.thanglong.project.usecase.Service.RedisService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // 1. Đánh dấu là một Spring Component để có thể inject dependency
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter { // 2. Kế thừa OncePerRequestFilter

    private final JwtProvider jwtService; // Đổi tên cho nhất quán
    private final UserDetailsService userDetailsService; // 3. Cần UserDetailsService để load thông tin user
    private final RedisService redisService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        // Nếu không có header hoặc header không đúng định dạng -> bỏ qua
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String jwt = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(jwt); // Username ở đây là email
        // 4. Chỉ xử lý nếu có token và người dùng chưa được xác thực
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            // Kiểm tra token có hợp lệ và không nằm trong danh sách đen (đã logout)
            if (jwtService.isTokenValid(jwt) && !redisService.isBlackListed(jwt)) {
                // Tạo đối tượng xác thực
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, // Dùng UserDetails thay vì chỉ username
                        null,
                        userDetails.getAuthorities() // Lấy quyền từ UserDetails
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Lưu thông tin xác thực vào SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
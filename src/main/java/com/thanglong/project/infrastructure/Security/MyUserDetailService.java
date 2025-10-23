package com.thanglong.project.usecase.service; // <-- Gói đề xuất

import com.thanglong.project.domain.repository.UserRepository;
import com.thanglong.project.infrastructure.Security.MyUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {

    private final UserRepository userRepository; // Inject repository để truy vấn DB

    @Override
    public MyUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Dùng UserRepository để tìm UserModel trong database theo email
        return userRepository.findByUsername(username)
                // 2. Nếu tìm thấy, chuyển đổi UserModel thành MyUserDetail
                .map(MyUserDetail::from)
                // 3. Nếu không tìm thấy, ném ra một exception chuẩn của Spring Security
                .orElseThrow(() ->
                        new UsernameNotFoundException("Không tìm thấy người dùng với username: " +username)
                );
    }
}
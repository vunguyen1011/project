package com.thanglong.project.infrastructure.Security; // Sửa lại package cho đúng

import com.thanglong.project.domain.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@Builder
public class MyUserDetail implements UserDetails {

    private final String username;
    private final String password;
    private final boolean isEnabled;
    private final Collection<SimpleGrantedAuthority> authorities;

    public static MyUserDetail from(UserModel user) {
        return MyUserDetail.builder()
                .username(user.getUsername()) // Dùng email làm username cho Spring Security
                .password(user.getPassword())
                .isEnabled(user.isActive())
                .authorities(user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities; // Trả về danh sách quyền thực tế
    }

    @Override
    public String getPassword() {
        return this.password; // Trả về mật khẩu thực tế
    }

    @Override
    public String getUsername() {
        return this.username; // Trả về username thực tế
    }

    // --- Các phương thức bắt buộc khác của UserDetails ---

    @Override
    public boolean isAccountNonExpired() {
        return true; // Mặc định là true, bạn có thể thêm logic nếu cần
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Mặc định là true
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Mặc định là true
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled; // Rất quan trọng: Trả về trạng thái active của user
    }
}
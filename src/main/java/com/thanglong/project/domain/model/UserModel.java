package com.thanglong.project.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password; // Mật khẩu chưa mã hóa ở lớp này
    private Set<RoleModel> roles;
    private Set<ProviderModel> providers;
    private boolean gender;
    private String phone;
    private boolean isActive;

}

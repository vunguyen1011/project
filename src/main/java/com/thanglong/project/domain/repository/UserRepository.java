package com.thanglong.project.domain.repository;



import com.thanglong.project.domain.model.UserModel;


import java.util.Optional;

public interface UserRepository {
    UserModel save(UserModel user);
    Optional<UserModel> findByUsername(String username);
    Optional<UserModel> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}

package com.thanglong.project.infrastructure.Impl;

import com.thanglong.project.domain.model.UserModel;
import com.thanglong.project.domain.repository.UserRepository;
import com.thanglong.project.infrastructure.Entity.User;
import com.thanglong.project.infrastructure.Mapper.UserMapper;
import com.thanglong.project.infrastructure.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public UserModel save(UserModel userModel) {
        User userEntity = userMapper.toEntity(userModel);
        User savedEntity = userJpaRepository.save(userEntity);
        return userMapper.toModel(savedEntity);
    }

    @Override
    public Optional<UserModel> findByUsername(String username) {
        return userJpaRepository.findByUsername(username)
                .map(userMapper::toModel);
    }

    @Override
    public Optional<UserModel> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(userMapper::toModel);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public List<UserModel> getAll() {
        return userJpaRepository.findAll().stream()
                .map(userMapper::toModel)
                .collect(Collectors.toList());
    }
}
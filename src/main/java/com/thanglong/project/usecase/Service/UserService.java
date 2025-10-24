package com.thanglong.project.usecase.Service;

import com.thanglong.project.domain.model.UserModel;
import com.thanglong.project.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
   public List<UserModel> getAll(){
       return userRepository.getAll();
   }
}

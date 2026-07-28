package com.example.usercrud.service;

import com.example.usercrud.dto.UserRequest;
import com.example.usercrud.dto.UserResponse;
import com.example.usercrud.entity.User;
import com.example.usercrud.repository.UserRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserResponse createUser(UserRequest request) {

        // DTO -> Entity
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());

        // Save to DB
        User savedUser = repository.save(user);

        // Entity -> DTO
        return new UserResponse(
                savedUser.getId(),
                savedUser.getUsername()
        );
    }
 
    public List<UserResponse> getAllUsers() {

    List<User> users = repository.findAll();

    return users.stream()
            .map(user -> new UserResponse(
                    user.getId(),
                    user.getUsername()))
            .toList();
}

public UserResponse getUserById(Long id) {

    User user = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

    return new UserResponse(
            user.getId(),
            user.getUsername()
    );
}
}
package com.example.usercrud.controller;

import com.example.usercrud.dto.UserRequest;
import com.example.usercrud.dto.UserResponse;
import com.example.usercrud.service.UserService;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest request) {
        return service.createUser(request);
    }


    @GetMapping
    public List<UserResponse> getAllUsers() {
    return service.getAllUsers();
}

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
    return service.getUserById(id);
}
}
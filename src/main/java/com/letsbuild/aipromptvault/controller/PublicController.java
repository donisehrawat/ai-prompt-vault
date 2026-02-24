package com.letsbuild.aipromptvault.controller;


import com.letsbuild.aipromptvault.dto.SignUpRequest;
import com.letsbuild.aipromptvault.entity.User;
import com.letsbuild.aipromptvault.enums.Role;
import com.letsbuild.aipromptvault.repository.UserRepo;
import com.letsbuild.aipromptvault.security.SpringSecurity;
import com.letsbuild.aipromptvault.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final UserRepo userRepo;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest request){

        if(userRepo.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)        // YOU decide
                .enabled(true)       // YOU decide
                .googleAccount(false)
                .createdAt(LocalDateTime.now())
                .build();

        userService.saveUser(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User created successfully");
    }

}

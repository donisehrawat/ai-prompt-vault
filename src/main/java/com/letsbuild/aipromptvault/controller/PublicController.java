package com.letsbuild.aipromptvault.controller;


import com.letsbuild.aipromptvault.entity.User;
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

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody User user){
        User newUser = User.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(user.getRole())
                .enabled(user.isEnabled())
                .googleAccount(user.isGoogleAccount())
                .createdAt(LocalDateTime.now())
                .build();

        userService.saveUser(newUser);

        if(newUser!=null) {
            return new ResponseEntity<>("User created", HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);

        }
    }

}

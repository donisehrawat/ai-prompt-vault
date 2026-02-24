package com.letsbuild.aipromptvault.controller;


import com.letsbuild.aipromptvault.entity.User;
import com.letsbuild.aipromptvault.repository.UserRepo;
import com.letsbuild.aipromptvault.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private UserService userService;

    private UserRepo userRepo;



}

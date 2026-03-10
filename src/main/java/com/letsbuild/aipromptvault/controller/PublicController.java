package com.letsbuild.aipromptvault.controller;


import com.letsbuild.aipromptvault.dto.PublicFeedResponse;
import com.letsbuild.aipromptvault.dto.SignUpRequest;
import com.letsbuild.aipromptvault.dto.ViewCreator;
import com.letsbuild.aipromptvault.entity.Prompt;
import com.letsbuild.aipromptvault.entity.User;
import com.letsbuild.aipromptvault.enums.Role;
import com.letsbuild.aipromptvault.repository.PromptRepo;
import com.letsbuild.aipromptvault.repository.UserRepo;
import com.letsbuild.aipromptvault.security.SpringSecurity;
import com.letsbuild.aipromptvault.service.PromptService;
import com.letsbuild.aipromptvault.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final UserRepo userRepo;

    private final PromptService promptService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest request){

        if(userRepo.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .enabled(true)
                .googleAccount(false)
                .createdAt(LocalDateTime.now())
                .build();

        userService.saveUser(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User created successfully");
    }


    @GetMapping("/all-prompts")
    public ResponseEntity<?> allPublicPrompts(){

        List<PublicFeedResponse> publicPrompts = promptService.getPublicPrompts();

        return new ResponseEntity<>(publicPrompts,HttpStatus.OK);

    }

    @GetMapping("/view-creator/{username}")
    public ResponseEntity<ViewCreator> viewCreator(@PathVariable String username){

        ViewCreator viewcreator = promptService.viewcreator(username);

        return ResponseEntity.ok(viewcreator);
    }
}

package com.letsbuild.aipromptvault.controller;


import com.letsbuild.aipromptvault.entity.Prompt;
import com.letsbuild.aipromptvault.entity.User;
import com.letsbuild.aipromptvault.repository.PromptRepo;
import com.letsbuild.aipromptvault.repository.UserRepo;
import com.letsbuild.aipromptvault.service.PromptService;
import com.letsbuild.aipromptvault.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/prompt")
@RequiredArgsConstructor
@Slf4j
public class PromptController {

    private final PromptService promptService;

    private final UserRepo userRepo;

    private final UserService userService;

    @PostMapping("/your-prompt")
    public ResponseEntity<?> yourPrompt(@RequestBody Prompt prompt) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        try {
            Prompt newPrompt = Prompt.builder()
                    .title(prompt.getTitle())
                    .content(prompt.getContent())
                    .tags(prompt.getTags())
                    .userId(user.getId())
                    .likeCount(0)
                    .visibility(prompt.getVisibility())
                    .createdAt(LocalDateTime.now())
                    .build();

            Prompt saved = promptService.savePrompt(newPrompt);

            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        } catch (Exception e) {
            log.error("Something went wrong while saving prompt", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong");
        }
    }
}

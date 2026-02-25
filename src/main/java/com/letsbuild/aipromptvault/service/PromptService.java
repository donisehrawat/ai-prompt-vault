package com.letsbuild.aipromptvault.service;

import com.letsbuild.aipromptvault.dto.CreatePromptRequest;
import com.letsbuild.aipromptvault.dto.PublicFeedResponse;
import com.letsbuild.aipromptvault.dto.ViewPrompts;
import com.letsbuild.aipromptvault.entity.Prompt;
import com.letsbuild.aipromptvault.entity.User;
import com.letsbuild.aipromptvault.enums.Visibility;
import com.letsbuild.aipromptvault.repository.PromptRepo;
import com.letsbuild.aipromptvault.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PromptService {

    private final UserRepo userRepo;

    private final PromptRepo promptRepo;

    private final UserService userService;

    public Prompt savePrompt(Prompt prompt){
        return promptRepo.save(prompt);
    }

    private ViewPrompts convertToDto(Prompt prompt) {

        return ViewPrompts.builder()
                .title(prompt.getTitle())
                .content(prompt.getContent())
                .tags(prompt.getTags().toString())
                .createdAt(prompt.getCreatedAt().toString())
                .build();
    }

    public List<ViewPrompts> getMyPrompts(String username){

        User user = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("user not found"));

        List<Prompt> myPrompts = promptRepo.findByUserId(user.getId());

        return myPrompts.stream()
                .map(this::convertToDto)
                .toList();

    }

    public void editPrompt(String promptId , String username , CreatePromptRequest newPrompt){

        User user = userService.findByUsername(username);

        Prompt prompt = promptRepo.findById(promptId)
                .orElseThrow(() -> new RuntimeException("Prompt not found"));

        if(!prompt.getUserId().equals(user.getId())){
           throw new RuntimeException("you are not allowed to edit this prompt");
        }else{


            if(newPrompt.getTitle()!=null){
                prompt.setTitle(newPrompt.getTitle());
            }

            if(newPrompt.getTags()!=null){
                prompt.setTags(newPrompt.getTags());
            }

            if(newPrompt.getContent()!=null){
                prompt.setContent(newPrompt.getContent());
            }

            prompt.setCreatedAt(LocalDateTime.now());

            promptRepo.save(prompt);

        }



    }

    public void deletePrompt(String id){

        Prompt prompt = promptRepo.findById(id).orElseThrow(() -> new RuntimeException("prompt not found"));

        promptRepo.deleteById(prompt.getId());
    }

    public List<PublicFeedResponse> getPublicPrompts() {

        List<Prompt> byVisibilityOrderByCreatedAtDesc = promptRepo.findByVisibilityOrderByCreatedAtDesc(Visibility.PUBLIC);

        return byVisibilityOrderByCreatedAtDesc.stream().map(this::convertToPublicDto).toList();

    }

    private PublicFeedResponse convertToPublicDto(Prompt prompt) {

        User user = userRepo.findById(prompt.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return PublicFeedResponse.builder()
                .id(prompt.getId())
                .title(prompt.getTitle())
                .username(user.getUsername())
                .likeCount(prompt.getLikeCount())
                .viewCount(prompt.getViewCount())
                .createdAt(prompt.getCreatedAt())
                .content(prompt.getContent())
                .tags(prompt.getTags())
                .build();
    }


}

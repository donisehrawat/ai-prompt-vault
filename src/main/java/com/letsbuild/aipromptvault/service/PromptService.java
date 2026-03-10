package com.letsbuild.aipromptvault.service;

import com.letsbuild.aipromptvault.dto.*;
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
import java.util.HashMap;
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

    public ViewCreator viewcreator(String username){

        User user = userService.findByUsername(username);
        String userId = user.getId();

        List<Prompt> byUserIdAndVisibility = promptRepo.findByUserIdAndVisibility(userId, Visibility.PUBLIC);

        List<PublicFeedResponse> promptDtos =
                byUserIdAndVisibility.stream()
                        .map(this::convertToPublicDto)
                        .toList();

        return ViewCreator.builder()
                .username(user.getUsername())
                .followers(user.getFollowers())
                .following(user.getFollowing())
                .prompts(promptDtos)
                .build();
    }

    public PromptSearchDto convertToPromptSearchDto(Prompt prompt){

        return PromptSearchDto.builder()
                .title(prompt.getTitle())
                .content(prompt.getContent())
                .tags(prompt.getTags())
                .build();

    }

    public UserSearchDto convertToUserSearchDto(User user){

        return UserSearchDto.builder()
                .username(user.getUsername())
                .build();

    }

    public ResponseEntity findButton(String keyword){

        List<Prompt> content = promptRepo.findByContentContainingIgnoreCase(keyword);
        List<Prompt> title = promptRepo.findByTitleContainingIgnoreCase(keyword);
        List<Prompt> tags = promptRepo.findByTagsContainingIgnoreCase(keyword);
        List<User> username = userRepo.findByUsernameContainingIgnoreCase(keyword);

        List<PromptSearchDto> contentDto = content.stream().map(this::convertToPromptSearchDto).toList();
        List<PromptSearchDto> titleDto = title.stream().map(this::convertToPromptSearchDto).toList();
        List<PromptSearchDto> tagsDto = tags.stream().map(this::convertToPromptSearchDto).toList();

        List<UserSearchDto> usernameDto = username.stream().map(this::convertToUserSearchDto).toList();

        HashMap<String,Object> result = new HashMap<>();

        result.put("Content Matches" , contentDto);
        result.put("Title Matches" , titleDto);
        result.put("username Matches" , usernameDto);
        result.put("Tags Matches" , tagsDto);

        return ResponseEntity.ok(result);

    }




}

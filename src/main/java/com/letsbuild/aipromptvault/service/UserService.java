package com.letsbuild.aipromptvault.service;


import com.letsbuild.aipromptvault.dto.PublicFeedResponse;
import com.letsbuild.aipromptvault.dto.SignUpRequest;
import com.letsbuild.aipromptvault.dto.ViewCreator;
import com.letsbuild.aipromptvault.entity.Prompt;
import com.letsbuild.aipromptvault.entity.User;
import com.letsbuild.aipromptvault.enums.Visibility;
import com.letsbuild.aipromptvault.repository.PromptRepo;
import com.letsbuild.aipromptvault.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    private final PromptRepo promptRepo;

    public User saveUser(User user){
         return userRepo.save(user);
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(String username){

        User user = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("user not found"));
        userRepo.delete(user);

    }

    public void editUser(SignUpRequest editUser, String username){

        User user = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("user not found"));

        if(editUser.getUsername()!=null){
            user.setUsername(editUser.getUsername());
        }

        if(editUser.getEmail()!=null){
            user.setEmail(editUser.getEmail());
        }

        if(editUser.getPassword()!=null){
            user.setPassword(editUser.getPassword());
        }

        userRepo.save(user);

    }












}

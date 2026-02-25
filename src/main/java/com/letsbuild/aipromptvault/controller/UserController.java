package com.letsbuild.aipromptvault.controller;


import com.letsbuild.aipromptvault.dto.SignUpRequest;
import com.letsbuild.aipromptvault.entity.User;
import com.letsbuild.aipromptvault.repository.UserRepo;
import com.letsbuild.aipromptvault.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserRepo userRepo;


    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        userService.deleteUser(username);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PutMapping("/edit-user")
    public ResponseEntity<?> editUser(@RequestBody SignUpRequest editUser){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        userService.editUser(editUser,username);

        return new ResponseEntity<>(HttpStatus.OK);


    }



}

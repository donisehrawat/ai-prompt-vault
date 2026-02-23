package com.letsbuild.aipromptvault.service;

import com.letsbuild.aipromptvault.entity.Prompt;
import com.letsbuild.aipromptvault.repository.PromptRepo;
import com.letsbuild.aipromptvault.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromptService {

    private final PromptRepo promptRepo;

    public Prompt savePrompt(Prompt prompt){
        return promptRepo.save(prompt);
    }

}

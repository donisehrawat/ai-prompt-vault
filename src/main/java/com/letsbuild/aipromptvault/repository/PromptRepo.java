package com.letsbuild.aipromptvault.repository;

import com.letsbuild.aipromptvault.entity.Prompt;
import com.letsbuild.aipromptvault.enums.Visibility;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PromptRepo extends MongoRepository<Prompt,String> {

    List<Prompt> findByUserId(String userId);

    List<Prompt> findByVisibilityOrderByCreatedAtDesc(Visibility visibility);

}

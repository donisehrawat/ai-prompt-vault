package com.letsbuild.aipromptvault.repository;

import com.letsbuild.aipromptvault.entity.Prompt;
import com.letsbuild.aipromptvault.enums.Visibility;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PromptRepo extends MongoRepository<Prompt,String> {

    List<Prompt> findByUserId(String userId);

    List<Prompt> findByVisibilityOrderByCreatedAtDesc(Visibility visibility);

    List<Prompt> findByUserIdAndVisibility(String userId, Visibility visibility);

    List<Prompt> findByTitleContainingIgnoreCase(String keyword);

    List<Prompt> findByContentContainingIgnoreCase(String keyword);

    List<Prompt> findByTagsContainingIgnoreCase(String keyword);

}

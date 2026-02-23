package com.letsbuild.aipromptvault.repository;

import com.letsbuild.aipromptvault.entity.Prompt;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PromptRepo extends MongoRepository<Prompt,String> {
}

package com.letsbuild.aipromptvault.repository;

import com.letsbuild.aipromptvault.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends MongoRepository<User,String> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    List<User> findByUsernameContainingIgnoreCase(String keyword);

}

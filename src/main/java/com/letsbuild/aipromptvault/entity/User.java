package com.letsbuild.aipromptvault.entity;

import com.letsbuild.aipromptvault.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private String id;

    private String username;

    private String password;

    private String email;

    private Role role;

    private boolean enabled;

    private boolean googleAccount;

    private LocalDateTime createdAt;

    private int followers;

    private int following;
}

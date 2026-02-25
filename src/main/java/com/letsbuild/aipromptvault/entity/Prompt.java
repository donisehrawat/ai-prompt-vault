package com.letsbuild.aipromptvault.entity;

import com.letsbuild.aipromptvault.enums.Visibility;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "prompt")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prompt {

    @Id
    private String id;

    private String title;

    private String content;

    private List<String> tags;

    private String userId;

    private Visibility visibility;

    private int likeCount;

    private int viewCount;

    private LocalDateTime createdAt;

}
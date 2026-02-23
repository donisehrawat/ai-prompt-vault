package com.letsbuild.aipromptvault.entity;

import com.letsbuild.aipromptvault.enums.Visibility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "prompt")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prompt {

    @Id
    private String id;

    private String title;

    private String content;

    private List<String> tags;

    private Visibility visibility;

    private int likeCount;

    private String userId;

    private LocalDateTime createdAt;
}

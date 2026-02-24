package com.letsbuild.aipromptvault.dto;

import com.letsbuild.aipromptvault.enums.Visibility;
import lombok.Data;

import java.util.List;

@Data
public class CreatePromptRequest {

    private String title;
    private String content;
    private List<String> tags;
    private Visibility visibility;

}

package com.letsbuild.aipromptvault.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ViewPrompts {

    private String title;
    private String content;
    private String createdAt;
    private String tags;
}

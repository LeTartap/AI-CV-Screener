package com.david.cv_screener_backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class AnalysisResponse {
    private String candidate;
    private List<SkillDTO> skills;
    private int score;
    private String feedback;
    private List<String> questions;

    @Data
    public static class SkillDTO {
        private String name;
        private double confidence;
    }
}

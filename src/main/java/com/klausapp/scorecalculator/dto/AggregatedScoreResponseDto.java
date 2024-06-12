package com.klausapp.scorecalculator.dto;

import lombok.*;

@Data
public class AggregatedScoreResponseDto {
    private String categoryName;
    private String date;
    private Float score;
}

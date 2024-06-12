package com.klausapp.scorecalculator.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RatingCategory {
    private Integer id;
    private String name;
    private Double weight;
}

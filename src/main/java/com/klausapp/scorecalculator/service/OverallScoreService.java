package com.klausapp.scorecalculator.service;

import com.klausapp.scorecalculator.repository.RatingRepository;
import org.springframework.stereotype.Service;

@Service
public class OverallScoreService {

    private final RatingRepository ratingRepository;

    public OverallScoreService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public float getOverallScore(String periodStart, String periodEnd) {
        Float score = ratingRepository.getOverallScore(periodStart, periodEnd);
        if (score == null) {
            throw new IllegalStateException("No record exists for period " + periodStart + " - " + periodEnd);
        }
        return score;
    }
}

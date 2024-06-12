package com.klausapp.scorecalculator.service;

import org.springframework.stereotype.Service;

@Service
public class ScoreChangeService {

    private final OverallScoreService overallScoreService;

    public ScoreChangeService(OverallScoreService overallScoreService) {
        this.overallScoreService = overallScoreService;
    }

    public float getScoreChangeOverPeriod(final String firstPeriodStart, final String firstPeriodEnd, final String secondPeriodStart, final String secondPeriodEnd) {
        Float firstPeriodScore = overallScoreService.getOverallScore(firstPeriodStart, firstPeriodEnd);
        Float secondPeriodScore = overallScoreService.getOverallScore(secondPeriodStart, secondPeriodEnd);
        return secondPeriodScore - firstPeriodScore;
    }
}

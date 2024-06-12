package com.klausapp.scorecalculator.repository;

import java.util.List;

public interface RatingRepository {
    List<CategoryScore> getDailyCategoryScores(String startDate, String endDate);

    List<CategoryScore> getWeeklyCategoryScores(String startDate, String endDate);

    List<TicketScore> getScoresOverPeriod(Integer categoryId, String startDate, String endDate);

    Float getOverallScore(String startDate, String endDate);

}

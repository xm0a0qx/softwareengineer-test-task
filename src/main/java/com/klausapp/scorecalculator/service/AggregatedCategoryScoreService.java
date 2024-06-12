package com.klausapp.scorecalculator.service;

import com.klausapp.scorecalculator.dto.AggregatedScoreResponseDto;
import com.klausapp.scorecalculator.repository.CategoryScore;
import com.klausapp.scorecalculator.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AggregatedCategoryScoreService {

    private final RatingRepository ratingRepository;

    public AggregatedCategoryScoreService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public List<AggregatedScoreResponseDto> getAggregatedScoreOverPeriod(String periodStart, String periodEnd) {
        LocalDateTime startDate = LocalDateTime.parse(periodStart);
        LocalDateTime endDate = LocalDateTime.parse(periodEnd);

        List<CategoryScore> scores = isWeeklyAggregated(startDate, endDate) ?
                ratingRepository.getWeeklyCategoryScores(periodStart, periodEnd) :
                ratingRepository.getDailyCategoryScores(periodStart, periodEnd);

        return getResponse(scores);
    }

    private boolean isWeeklyAggregated(LocalDateTime startDate, LocalDateTime endDate) {
        return startDate.plusMonths(1).isBefore(endDate);
    }

    private List<AggregatedScoreResponseDto> getResponse(List<CategoryScore> result) {
        return result.stream().map(score -> {
            AggregatedScoreResponseDto dto = new AggregatedScoreResponseDto();
            dto.setCategoryName(score.getCategory());
            dto.setDate(score.getDate());
            dto.setScore(score.getScore());
            return dto;
        }).toList();
    }
}

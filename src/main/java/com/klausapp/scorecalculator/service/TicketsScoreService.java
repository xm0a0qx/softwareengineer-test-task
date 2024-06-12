package com.klausapp.scorecalculator.service;

import com.klausapp.scorecalculator.dto.TicketScoreResponseDto;
import com.klausapp.scorecalculator.repository.RatingCategoryRepository;
import com.klausapp.scorecalculator.repository.RatingRepository;
import com.klausapp.scorecalculator.repository.TicketScore;
import com.klausapp.scorecalculator.repository.model.RatingCategory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TicketsScoreService {

    private final RatingCategoryRepository ratingCategoryRepository;
    private final RatingRepository ratingRepository;

    public TicketsScoreService(RatingCategoryRepository ratingCategoryRepository, RatingRepository ratingRepository) {
        this.ratingCategoryRepository = ratingCategoryRepository;
        this.ratingRepository = ratingRepository;
    }

    public Map<Integer, List<TicketScoreResponseDto>> getTicketScore(String periodStart, String periodEnd) {
        HashMap<Integer, List<TicketScoreResponseDto>> response = new HashMap<>();
        List<RatingCategory> categories = ratingCategoryRepository.getAllRatingCategories();

        categories.forEach(ratingCategory -> {
            List<TicketScore> ticketScores = ratingRepository.getScoresOverPeriod(ratingCategory.getId(), periodStart, periodEnd);
            ticketScores.forEach(score -> {
                Integer ticketId = score.getTicketId();
                if (response.get(score.getTicketId()) == null) {
                    response.put(ticketId, new ArrayList<>());
                }
                TicketScoreResponseDto dto = generateDto(ratingCategory, score);
                response.get(ticketId).add(dto);
            });
        });
        return response;
    }

    private static TicketScoreResponseDto generateDto(RatingCategory ratingCategory, TicketScore ticketScore) {
        TicketScoreResponseDto dto = new TicketScoreResponseDto();
        dto.setCategoryName(ratingCategory.getName());
        dto.setScore(ticketScore.getScore());
        return dto;
    }
}

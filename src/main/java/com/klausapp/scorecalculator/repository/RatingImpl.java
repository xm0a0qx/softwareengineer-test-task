package com.klausapp.scorecalculator.repository;

import com.klausapp.scorecalculator.repository.mapper.CategoryScoreMapper;
import com.klausapp.scorecalculator.repository.mapper.TicketsScoreMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RatingImpl implements RatingRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public RatingImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<CategoryScore> getDailyCategoryScores(String periodStart, String periodEnd) {
        String sql = """
                SELECT rc.name AS category, r.created_at AS date, AVG(r.rating) * 20 AS score
                FROM ratings r JOIN rating_categories rc ON rc.id = r.rating_category_id
                WHERE r.created_at >= :periodStart
                AND r.created_at <= :periodEnd
                GROUP BY strftime('%Y-%m-%d', r.created_at), rc.id;

                """;
        Map<String, Object> params = new HashMap<>();
        params.put("periodStart", periodStart);
        params.put("periodEnd", periodEnd);

        return namedParameterJdbcTemplate.query(sql, params, CategoryScoreMapper.getInstance());
    }

    @Override
    public List<CategoryScore> getWeeklyCategoryScores(String periodStart, String periodEnd) {
        String sql = """
                SELECT rc.name AS category, r.created_at AS date, AVG(r.rating) * 20 AS score
                FROM ratings r JOIN rating_categories rc ON rc.id = r.rating_category_id
                WHERE r.created_at >= :periodStart
                AND r.created_at <= :periodEnd
                GROUP BY strftime('%Y-%W', r.created_at), rc.id;
                """;
        Map<String, Object> params = new HashMap<>();
        params.put("periodStart", periodStart);
        params.put("periodEnd", periodEnd);

        return namedParameterJdbcTemplate.query(sql, params, CategoryScoreMapper.getInstance());
    }

    @Override
    public List<TicketScore> getScoresOverPeriod(Integer categoryId, String periodStart, String periodEnd) {
        String sql = """
                SELECT r.ticket_id AS ticketId, SUM((rating * 20)) AS score FROM ratings AS r
                WHERE r.created_at >= :periodStart AND r.created_at <= :periodEnd
                AND rating_category_id = :categoryId GROUP BY ticket_id
                """;
        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);
        params.put("periodStart", periodStart);
        params.put("periodEnd", periodEnd);

        return namedParameterJdbcTemplate.query(sql, params, TicketsScoreMapper.getInstance());
    }

    @Override
    public Float getOverallScore(String periodStart, String periodEnd) {
        String sql = """
                SELECT (SUM(r.rating * rc.weight) / SUM(rc.weight)) * 20 AS score
                FROM ratings r JOIN rating_categories rc ON rc.id = r.rating_category_id
                WHERE r.created_at >= :periodStart
                AND r.created_at <= :periodEnd
                """;
        Map<String, Object> params = new HashMap<>();
        params.put("periodStart", periodStart);
        params.put("periodEnd", periodEnd);

        return namedParameterJdbcTemplate.queryForObject(sql, params, Float.class);
    }
}

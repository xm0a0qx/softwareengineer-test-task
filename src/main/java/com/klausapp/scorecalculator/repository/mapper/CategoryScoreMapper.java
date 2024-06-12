package com.klausapp.scorecalculator.repository.mapper;

import com.klausapp.scorecalculator.repository.CategoryScore;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryScoreMapper implements RowMapper<CategoryScore> {
    private static final CategoryScoreMapper INSTANCE = new CategoryScoreMapper();

    public static CategoryScoreMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public CategoryScore mapRow(ResultSet rs, int rowNum) throws SQLException {
        String category = rs.getString("category");
        String date = rs.getString("date");
        Float score = rs.getFloat("score");
        return new CategoryScore() {
            @Override
            public String getCategory() {
                return category;
            }

            @Override
            public String getDate() {
                return date;
            }

            @Override
            public Float getScore() {
                return score;
            }
        };
    }
}

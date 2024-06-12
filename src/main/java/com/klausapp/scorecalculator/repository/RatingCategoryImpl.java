package com.klausapp.scorecalculator.repository;

import com.klausapp.scorecalculator.repository.mapper.RatingCategoryMapper;
import com.klausapp.scorecalculator.repository.model.RatingCategory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RatingCategoryImpl implements RatingCategoryRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public RatingCategoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<RatingCategory> getAllRatingCategories() {
        String sql = """
                SELECT id, name, weight FROM rating_categories
                """;
        return namedParameterJdbcTemplate.query(sql, RatingCategoryMapper.getInstance());
    }
}

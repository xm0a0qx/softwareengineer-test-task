package com.klausapp.scorecalculator.repository.mapper;

import com.klausapp.scorecalculator.repository.model.RatingCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingCategoryMapper implements RowMapper<RatingCategory> {
    private static final RatingCategoryMapper INSTANCE = new RatingCategoryMapper();

    public static RatingCategoryMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public RatingCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new RatingCategory(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDouble("weight")
        );
    }
}

package com.klausapp.scorecalculator.repository;

import com.klausapp.scorecalculator.repository.model.RatingCategory;

import java.util.List;

public interface RatingCategoryRepository {
    List<RatingCategory> getAllRatingCategories();
}

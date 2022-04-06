package com.example.restoranvoting.repository;

import com.example.restoranvoting.model.Meal;

import java.util.List;

public interface MealRepository extends BaseRepository<Meal> {

    List<Meal> getAllByRestaurantId(int id);
}

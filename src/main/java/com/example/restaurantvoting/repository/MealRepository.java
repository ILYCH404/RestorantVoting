package com.example.restaurantvoting.repository;

import com.example.restaurantvoting.model.Meal;

import java.util.List;

public interface MealRepository extends BaseRepository<Meal> {

    List<Meal> getAllByRestaurantId(int id);
}

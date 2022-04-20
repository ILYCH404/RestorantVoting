package com.example.restaurantvoting.repository;

import com.example.restaurantvoting.model.Meal;
import com.example.restaurantvoting.model.Menu;
import com.example.restaurantvoting.to.MealTo;

import java.util.List;

public interface MealRepository extends BaseRepository<Meal> {
    List<MealTo> getAllByMenu(Menu menu);
}

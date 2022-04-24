package com.example.restaurantvoting.repository;

import com.example.restaurantvoting.model.Meal;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public interface MealRepository extends BaseRepository<Meal> {

    @Modifying
    @Query("SELECT u FROM Meal u")
    Set<Meal> getMeal();
}

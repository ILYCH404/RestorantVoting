package com.example.restoranvoting.repository;

import com.example.restoranvoting.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, Integer> {
}

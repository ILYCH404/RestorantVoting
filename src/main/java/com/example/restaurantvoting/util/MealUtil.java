package com.example.restaurantvoting.util;

import com.example.restaurantvoting.model.Meal;
import com.example.restaurantvoting.to.MealTo;

public class MealUtil {

    public static Meal createFromTo(MealTo mealTo) {
        return new Meal(null, mealTo.getDescription(), mealTo.getPrice());
    }

    public static Meal updateFromTo(Meal meal, MealTo mealTo) {
        meal.setDescription(mealTo.getDescription());
        meal.setPrice(mealTo.getPrice());
        return meal;
    }
}


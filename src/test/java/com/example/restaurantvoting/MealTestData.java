package com.example.restaurantvoting;

import com.example.restaurantvoting.model.Meal;

import java.util.List;

public class MealTestData {

    public static final MatcherFactory.Matcher<Meal> MEAL_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Meal.class);

    public static final int MEAL1_ID = 1;

    public static final Meal meal1 = new Meal(MEAL1_ID, "Манная каша", 100);
    public static final Meal meal2 = new Meal(MEAL1_ID + 1, "Солянка", 500);
    public static final Meal meal3 = new Meal(MEAL1_ID + 2, "Пюре с котлетой", 700);
    public static final List<Meal> mealsForRestaurant1 = List.of(meal3, meal2, meal1);
    public static final Meal meal4 = new Meal(MEAL1_ID + 3, "Стейк", 1500);
    public static final Meal meal5 = new Meal(MEAL1_ID + 4, "Борщ", 1000);
    public static final Meal meal6 = new Meal(MEAL1_ID + 5, "Селедка под шубой", 500);
    public static final Meal meal7 = new Meal(MEAL1_ID + 6, "Картошка", 500);
    public static final Meal meal8 = new Meal(MEAL1_ID + 7, "Блины", 600);

    public static Meal getNew() {
        return new Meal(null, "Созданное блюдо", 500);
    }

    public static Meal getUpdated() {
        return new Meal(MEAL1_ID, "Обновленная манная каша", 200);
    }
}

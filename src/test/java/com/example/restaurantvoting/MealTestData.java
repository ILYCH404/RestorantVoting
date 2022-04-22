package com.example.restaurantvoting;

import com.example.restaurantvoting.model.Meal;

public class MealTestData {

    public static final MatcherFactory.Matcher<Meal> MEAL_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Meal.class);

    public static final int MEAL1_ID = 1;

    public static final Meal meal1 = new Meal(MEAL1_ID, "Манная каша", 100);

    public static Meal getNew() {
        return new Meal(null, "Созданное блюдо", 500);
    }

    public static Meal getUpdated() {
        return new Meal(MEAL1_ID, "Обновленная манная каша", 200);
    }
}

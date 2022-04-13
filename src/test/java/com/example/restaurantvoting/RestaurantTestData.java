package com.example.restaurantvoting;

import com.example.restaurantvoting.model.Restaurant;

import static com.example.restaurantvoting.MealTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class);

    public static MatcherFactory.Matcher<Restaurant> RESTAURANT_WITH_MEALS_MATCHER =
            MatcherFactory.usingAssertions(Restaurant.class,
                    (a, e) -> assertThat(a).usingRecursiveComparison(),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });

    public static final int RESTAURANT1_ID = 1;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Клод-Моне", "Невский проспект");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT1_ID + 1, "Зима", "Сенная");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT1_ID + 2, "Чернигов", "Дыбенко");
    public static final Restaurant restarauntWithMeals1 = new Restaurant(RESTAURANT1_ID, "Чернигов", "Дыбенко");

    static {
        restarauntWithMeals1.setMeals(mealsForRestaurant1);
    }


    public static Restaurant getNew() {
        return new Restaurant(null, "Созданный ресторан", "Созданный адрес");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(MEAL1_ID, "Обновленный ресторан", "Обновленный адрес");
    }
}

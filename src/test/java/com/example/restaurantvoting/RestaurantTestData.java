package com.example.restaurantvoting;

import com.example.restaurantvoting.model.Meal;
import com.example.restaurantvoting.model.Restaurant;

import java.util.List;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class);

    public static final int RESTAURANT1_ID = 1;
    public static final int RESTAURANT_NOT_FOUND = 100;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Клод-Моне", "Невский проспект");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT1_ID + 1, "Зима", "Сенная");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT1_ID + 2, "Чернигов", "Дыбенко");

    public static final List<Restaurant> restaurants = List.of(restaurant1, restaurant2, restaurant3);

    public static Restaurant getNew() {
        return new Restaurant(null, "Созданный ресторан", "Созданный адрес");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID, "Обновленный ресторан", "Обновленный адрес");
    }
}

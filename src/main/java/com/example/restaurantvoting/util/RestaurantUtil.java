package com.example.restaurantvoting.util;

import com.example.restaurantvoting.model.Restaurant;
import com.example.restaurantvoting.to.RestaurantTo;

public class RestaurantUtil {

    public static Restaurant createNewFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(null, restaurantTo.getAddress(), restaurantTo.getDescription());
    }

    public static Restaurant updateFromTo(Restaurant restaurant, RestaurantTo restaurantTo) {
        restaurant.setAddress(restaurantTo.getAddress());
        restaurant.setDescription(restaurantTo.getDescription());
        return restaurant;
    }
}

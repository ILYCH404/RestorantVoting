package com.example.restoranvoting.repository;

import com.example.restoranvoting.model.Restaurant;

import java.util.Optional;

public interface RestaurantRepository extends BaseRepository<Restaurant> {
    Optional<Restaurant> findByDescription(String description);
}

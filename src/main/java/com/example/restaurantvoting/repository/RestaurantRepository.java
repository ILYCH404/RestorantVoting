package com.example.restaurantvoting.repository;

import com.example.restaurantvoting.model.Restaurant;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public interface RestaurantRepository extends BaseRepository<Restaurant> {
    Optional<Restaurant> findByDescription(String description);

    Optional<Restaurant> getByAddress(@NotBlank String address);
}

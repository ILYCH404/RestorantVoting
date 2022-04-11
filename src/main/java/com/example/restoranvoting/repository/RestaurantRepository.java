package com.example.restoranvoting.repository;

import com.example.restoranvoting.model.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RestaurantRepository extends BaseRepository<Restaurant> {
    Optional<Restaurant> findByDescription(String description);

    @EntityGraph(attributePaths = {"meals"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT u FROM Restaurant u WHERE u.id=?1")
    Optional<Restaurant> getWithMeals(int id);
}

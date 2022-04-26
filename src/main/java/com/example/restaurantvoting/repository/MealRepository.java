package com.example.restaurantvoting.repository;

import com.example.restaurantvoting.model.Meal;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Optional;
import java.util.Set;

public interface MealRepository extends BaseRepository<Meal> {

    @Modifying
    @Query("SELECT u FROM Meal u")
    Set<Meal> getMeal();

    Optional<Meal> findByDescription(@NotBlank @Size(min = 2, max = 120) String description);
}

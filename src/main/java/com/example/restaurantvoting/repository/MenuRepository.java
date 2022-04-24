package com.example.restaurantvoting.repository;

import com.example.restaurantvoting.model.Menu;

import java.util.Optional;

public interface MenuRepository extends BaseRepository<Menu> {
    boolean existsMenuByRestaurantId(Integer restaurant_id);

    Menu getMenuByRestaurantId(Integer restaurant_id);

    Optional<Menu> findMenuByRestaurantId(Integer restaurant_id);
}

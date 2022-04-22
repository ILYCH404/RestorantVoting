package com.example.restaurantvoting.repository;

import com.example.restaurantvoting.model.Menu;

public interface MenuRepository extends BaseRepository<Menu> {
    boolean existsMenuByRestaurantId(Integer restaurant_id);

    Menu getMenuByRestaurantId(Integer restaurant_id);
}

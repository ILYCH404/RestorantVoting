package com.example.restaurantvoting.web.menu;

import com.example.restaurantvoting.model.Meal;
import com.example.restaurantvoting.model.Menu;
import com.example.restaurantvoting.model.Restaurant;
import com.example.restaurantvoting.repository.MealRepository;
import com.example.restaurantvoting.repository.MenuRepository;
import com.example.restaurantvoting.repository.RestaurantRepository;
import com.example.restaurantvoting.to.MealTo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(value = MenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
// TODO: cache only most requested data!
@CacheConfig(cacheNames = "menu")
@Api
public class MenuRestController {
    static final String REST_URL = "/api/admin/menu";

    private final Random random = new Random();

    @Autowired
    protected MealRepository mealRepository;
    @Autowired
    protected RestaurantRepository restaurantRepository;
    @Autowired
    protected MenuRepository menuRepository;

    @GetMapping("/{id}")
    public List<MealTo> getMenu(@PathVariable int id) {
        return mealRepository.getAllByMenu(menuRepository.getMenuByRestaurantId(id));
    }

    @GetMapping("/createMenu/{restaurant_id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void createMenuForRestaurant(@PathVariable int restaurant_id) {
        menuRepository.deleteByRestaurantId(restaurant_id);
        Restaurant restaurant = restaurantRepository.getById(restaurant_id);
        Menu menu = new Menu(restaurant);
        int meals_count = random.nextInt(3) + 2;
        while (meals_count-- >= 1) {
            int meals_id = Math.toIntExact(random.nextLong(mealRepository.count()) + 1);
            Meal meal = mealRepository.getById(meals_id);
            if (meal.getMenu() != null) {
                meals_count++;
                continue;
            }
            meal.setMenu(menu);
        }
    }
}

package com.example.restaurantvoting.web.menu;

import com.example.restaurantvoting.model.Meal;
import com.example.restaurantvoting.model.Menu;
import com.example.restaurantvoting.repository.MealRepository;
import com.example.restaurantvoting.repository.MenuRepository;
import com.example.restaurantvoting.repository.RestaurantRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

import static com.example.restaurantvoting.util.validation.ValidationUtil.checkTimeForUpdateMenu;

@RestController
@RequestMapping(value = MenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
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
    public Optional<Menu> get(@PathVariable int id) {
        return Objects.requireNonNull(menuRepository.findMenuByRestaurantId(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void delete(@PathVariable int id) {
        Menu menu = menuRepository.getById(id);
        menu.getMenu().forEach(meal -> meal.setMenu(null));
        menu.setRestaurant(null);
        menuRepository.deleteExisted(id);
    }

    @GetMapping("/createMenu/{restaurant_id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void createMenuForRestaurant(@PathVariable int restaurant_id) {
        Set<Meal> meals = new HashSet<>();
        List<Meal> random_meals = mealRepository.getMeal().stream().toList();
        Menu menu;
        if (menuRepository.existsMenuByRestaurantId(restaurant_id)) {
            menu = menuRepository.getMenuByRestaurantId(restaurant_id);
            checkTimeForUpdateMenu(menu.getAdded());
            menu.getMenu().clear();
            menu.setAdded(LocalDate.now());
        } else {
            menu = new Menu(restaurantRepository.getById(restaurant_id));
        }
        int meals_count = random.nextInt(4) + 2;
        while (meals_count-- >= 1) {
            Meal meal = random_meals.get(random.nextInt(random_meals.size()));
            if (meals.contains(meal)) {
                meals_count++;
                continue;
            }
            meals.add(meal);
        }
        menu.setMenu(meals);
        menuRepository.save(menu);
    }
}

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
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

import static com.example.restaurantvoting.util.validation.ValidationUtil.*;

@RestController
@RequestMapping(value = MenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@CacheConfig(cacheNames = "menu")
@Api
@Scope("prototype")
public class MenuRestController {
    static final String REST_URL = "/api/admin/menu";

    @Autowired
    protected MealRepository mealRepository;
    @Autowired
    protected RestaurantRepository restaurantRepository;
    @Autowired
    protected MenuRepository menuRepository;

    @GetMapping("/{id}")
    @CacheEvict(allEntries = true)
    public Optional<Menu> get(@PathVariable int id) {
        return Objects.requireNonNull(menuRepository.findMenuByRestaurantId(id));
    }

    @GetMapping()
    @CacheEvict(allEntries = true)
    public List<Menu> getAll() {
        return menuRepository.findAll();
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
        List<Meal> random_meals = new ArrayList<>(mealRepository.getMeal().stream().toList());
        checkMealCount(random_meals.size());
        Set<Meal> meals = new HashSet<>();
        Menu menu;
        if (menuRepository.existsMenuByRestaurantId(restaurant_id)) {
            menu = menuRepository.getMenuByRestaurantId(restaurant_id);
            checkTimeForUpdateMenu(menu.getAdded());
            menu.getMenu().clear();
            menu.setAdded(LocalDate.now());
        } else {
            menu = new Menu(restaurantRepository.getById(restaurant_id));
        }
        int mealsCount = crateRandomNumberWithMin(2, 5);
        while (mealsCount >= 1) {
            Meal meal = random_meals.get(crateRandomNumberWithMax(random_meals.size()));
            meals.add(meal);
            random_meals.remove(meal);
            mealsCount--;
        }
        menu.setMenu(meals);
        menuRepository.save(menu);
    }
}

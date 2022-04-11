package com.example.restoranvoting.web.meal;

import com.example.restoranvoting.model.Meal;
import com.example.restoranvoting.model.Restaurant;
import com.example.restoranvoting.repository.MealRepository;
import com.example.restoranvoting.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Random;

import static com.example.restoranvoting.util.validation.ValidationUtil.assureIdConsistent;
import static com.example.restoranvoting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
// TODO: cache only most requested data!
@CacheConfig(cacheNames = "meals")
public class MealRestController {

    static final String REST_URL = "/admin/meals";

    @Autowired
    protected MealRepository repository;
    @Autowired
    protected RestaurantRepository restaurantRepository;

    @GetMapping("/{restaurant_id}/menu")
    @Cacheable
    public List<Meal> getMenu(@PathVariable int restaurant_id) {
        return repository.getAllByRestaurantId(restaurant_id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meal> get(@PathVariable int id) {
        return ResponseEntity.of(repository.findById(id));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
    public ResponseEntity<Meal> createWithLocation(@Valid @RequestBody Meal meal) {
        checkNew(meal);
        Meal created = repository.save(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void update(@Valid @RequestBody Meal meal, @PathVariable int id) {
        assureIdConsistent(meal, id);
        repository.save(meal);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        repository.delete(id);
    }

    @PostMapping("/createMenu/{restaurant_id}")
    @Transactional
    public void createMenuForRestaurant(@PathVariable int restaurant_id) {
        repository.getAllByRestaurantId(restaurant_id).forEach(meal -> meal.setRestaurant(null));

        Restaurant restaurant = restaurantRepository.getById(restaurant_id);
        Random random = new Random();
        int meals_count = random.nextInt(3) + 2;

        while (meals_count-- >= 1) {
            int meals_id = Math.toIntExact(random.nextLong(repository.count()) + 1);
            Meal meal = repository.getById(meals_id);
            if (meal.getRestaurant() != null) {
                meals_count++;
                continue;
            }
            meal.setRestaurant(restaurant);
        }
    }
}

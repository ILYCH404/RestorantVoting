package com.example.restoranvoting.web.meal;

import com.example.restoranvoting.model.Meal;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.example.restoranvoting.util.validation.ValidationUtil.assureIdConsistent;
import static com.example.restoranvoting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
// TODO: cache only most requested data!
@CacheConfig(cacheNames = "meals")
public class MealRestController {

    static final String REST_URL = "/api/admin/restaurant";

    @Autowired
    protected MealRepository repository;
    @Autowired
    protected RestaurantRepository restaurantRepository;

    @GetMapping("/{restaurant_id}/meals")
    @Cacheable
    public List<Meal> getAll(@PathVariable int restaurant_id) {
        log.info("getAll for restaurant {}", restaurant_id);
        return repository.getAllByRestaurantId(restaurant_id);
    }

    @GetMapping("meals/{id}")
    public ResponseEntity<Meal> get(@PathVariable int id) {
        log.info("get {}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    @PostMapping(value = "/{restaurant_id}/meals", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
    public ResponseEntity<Meal> createWithLocation(@Valid @RequestBody Meal meal, @PathVariable int restaurant_id) {
        log.info("create{} for restaurant {}", meal, restaurant_id);
        checkNew(meal);
        meal.setRestaurant(restaurantRepository.getById(restaurant_id));
        Meal created = repository.save(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PostMapping(value = "/{restaurant_id}/meals/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void update(@Valid @RequestBody Meal meal, @PathVariable int id, @PathVariable int restaurant_id) {
        log.info("update {} with id {} from restaurant {}", meal, id, restaurant_id);
        assureIdConsistent(meal, id);
        meal.setRestaurant(restaurantRepository.getById(restaurant_id));
        repository.save(meal);
    }

    @DeleteMapping("/meals/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        repository.delete(id);
    }
}

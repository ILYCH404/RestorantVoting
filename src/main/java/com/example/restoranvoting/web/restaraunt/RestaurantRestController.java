package com.example.restoranvoting.web.restaraunt;

import com.example.restoranvoting.model.Meal;
import com.example.restoranvoting.model.Restaurant;
import com.example.restoranvoting.repository.MealRepository;
import com.example.restoranvoting.repository.RestaurantRepository;
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
import java.util.Objects;

import static com.example.restoranvoting.util.validation.ValidationUtil.assureIdConsistent;
import static com.example.restoranvoting.util.validation.ValidationUtil.checkNew;


@RestController
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@CacheConfig(cacheNames = "restaurant")
public class RestaurantRestController {
    static final String REST_URL = "/admin/restaurants";

    @Autowired
    protected RestaurantRepository restaurantRepository;

    @GetMapping
    @Cacheable
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        return ResponseEntity.of(Objects.requireNonNull(restaurantRepository.findById(id)));
    }

    @GetMapping("/withMeals/{id}")
    public ResponseEntity<Restaurant> getWithMeals(@PathVariable int id) {
        return ResponseEntity.of(Objects.requireNonNull(restaurantRepository.getWithMeals(id)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "restaurant", allEntries = true)
    public void delete(@PathVariable int id) {
        restaurantRepository.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        checkNew(restaurant);
        Restaurant created = restaurantRepository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        assureIdConsistent(restaurant, id);
        restaurantRepository.save(restaurant);
    }

    @GetMapping("/by-description")
    public ResponseEntity<Restaurant> getByDescription(@RequestParam String description) {
        return ResponseEntity.of(restaurantRepository.findByDescription(description));
    }
}

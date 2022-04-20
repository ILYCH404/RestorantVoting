package com.example.restaurantvoting.web.restaraunt;

import com.example.restaurantvoting.model.Restaurant;
import com.example.restaurantvoting.repository.RestaurantRepository;
import com.example.restaurantvoting.to.RestaurantTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Objects;

import static com.example.restaurantvoting.util.RestaurantUtil.createNewFromTo;
import static com.example.restaurantvoting.util.RestaurantUtil.updateFromTo;
import static com.example.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static com.example.restaurantvoting.util.validation.ValidationUtil.checkNew;


@RestController
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@CacheConfig(cacheNames = "restaurant")
public class RestaurantRestController {
    static final String REST_URL = "/api/admin/restaurants";

    @Autowired
    protected RestaurantRepository restaurantRepository;

    @Autowired
    private UniqueValidatorForRestaurant validatorForRestaurant;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(validatorForRestaurant);
    }

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
        restaurantRepository.deleteExisted(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody RestaurantTo restaurantTo) {
        checkNew(restaurantTo);
        Restaurant created = restaurantRepository.save(createNewFromTo(restaurantTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Transactional
    public void update(@Valid @RequestBody RestaurantTo restaurantTo, @PathVariable int id) {
        assureIdConsistent(createNewFromTo(restaurantTo), id);
        Restaurant restaurant = restaurantRepository.getById(id);
        restaurantRepository.save(updateFromTo(restaurant, restaurantTo));
    }

    @GetMapping("/by-description")
    public ResponseEntity<Restaurant> getByDescription(@RequestParam String description) {
        return ResponseEntity.of(restaurantRepository.findByDescription(description));
    }
}

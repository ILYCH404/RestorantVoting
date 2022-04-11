package com.example.restoranvoting.web.restaraunt;

import com.example.restoranvoting.model.Restaurant;
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
import java.util.Objects;

import static com.example.restoranvoting.util.validation.ValidationUtil.assureIdConsistent;
import static com.example.restoranvoting.util.validation.ValidationUtil.checkNew;


@RestController
@RequestMapping(value = RestaurantRestController.REST_URL,produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "restaurant")
public class RestaurantRestController {
    static final String REST_URL = "/admin/restaurants";

    @Autowired
    protected RestaurantRepository repository;

    @GetMapping
    @Cacheable
    public List<Restaurant> getAll() {
        log.info("getAll");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        log.info("get {}", id);
        return ResponseEntity.of(Objects.requireNonNull(repository.findById(id)));
    }

    @GetMapping("/withMeals/{id}")
    public ResponseEntity<Restaurant> getWithMeals(@PathVariable int id) {
        log.info("get {}", id);
        return ResponseEntity.of(Objects.requireNonNull(repository.getWithMeals(id)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "restaurant", allEntries = true)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        repository.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        Restaurant created = repository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void update(@Valid @RequestBody Restaurant restaurant,@PathVariable int id) {
        log.info("update {} with id {}", restaurant, id);
        assureIdConsistent(restaurant, id);
        repository.save(restaurant);
    }

    @GetMapping("/by-description")
    public ResponseEntity<Restaurant> getByDescription(@RequestParam String description) {
        log.info("getByDescription {}", description);
        return ResponseEntity.of(repository.findByDescription(description));
    }
}

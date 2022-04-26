package com.example.restaurantvoting.web.meal;

import com.example.restaurantvoting.model.Meal;
import com.example.restaurantvoting.repository.MealRepository;
import com.example.restaurantvoting.repository.RestaurantRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
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

import static com.example.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static com.example.restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@CacheConfig(cacheNames = "meals")
@Api
public class MealRestController {

    static final String REST_URL = "/api/admin/meals";


    @Autowired
    protected MealRepository mealRepository;
    @Autowired
    protected RestaurantRepository restaurantRepository;

    @Autowired
    private UniqueMealValidator uniqueMealValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(uniqueMealValidator);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meal> get(@PathVariable int id) {
        return ResponseEntity.of(Objects.requireNonNull(mealRepository.findById(id)));
    }

    @GetMapping()
    @CacheEvict(allEntries = true)
    public List<Meal> getAll() {
        return mealRepository.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
    public ResponseEntity<Meal> createWithLocation(@Valid @RequestBody Meal meal) {
        checkNew(meal);
        Meal created = mealRepository.save(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Transactional
    public void update(@Valid @RequestBody Meal meal, @PathVariable int id) {
        assureIdConsistent(meal, id);
        mealRepository.save(meal);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void delete(@PathVariable int id) {
        Meal meal = mealRepository.getById(id);
        meal.getMenu().forEach(menu -> menu.getMenu().remove(meal));
        mealRepository.delete(meal);
    }
}

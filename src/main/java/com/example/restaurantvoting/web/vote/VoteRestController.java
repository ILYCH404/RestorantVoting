package com.example.restaurantvoting.web.vote;

import com.example.restaurantvoting.model.Restaurant;
import com.example.restaurantvoting.model.Vote;
import com.example.restaurantvoting.repository.RestaurantRepository;
import com.example.restaurantvoting.repository.UserRepository;
import com.example.restaurantvoting.repository.VoteRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static com.example.restaurantvoting.util.validation.ValidationUtil.checkTime;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@CacheConfig(cacheNames = "vote")
public class VoteRestController {
    static final String REST_URL = "/api/profile";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private VoteRepository voteRepository;


    @DeleteMapping("/{user_id}/votes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int user_id) {
        checkTime();
        voteRepository.delete(voteRepository.findByUserId(user_id));
    }

    @SneakyThrows
    @PostMapping("/{user_id}/votes/{restaurant_id}")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    @CacheEvict(allEntries = true)
    public void vote(@PathVariable int user_id, @PathVariable int restaurant_id) {
        Restaurant restaurant = restaurantRepository.getById(restaurant_id);
        restaurant.id();
        if (voteRepository.existsByUserId(user_id)) {
            if (checkTime()) {
                voteRepository.setRestaurant(restaurant, user_id);
            }
        } else {
            Vote newVote = new Vote(userRepository.getById(user_id), restaurant);
            voteRepository.save(newVote);
        }
    }
}

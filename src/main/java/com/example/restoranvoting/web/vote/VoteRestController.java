package com.example.restoranvoting.web.vote;

import com.example.restoranvoting.model.Vote;
import com.example.restoranvoting.repository.RestaurantRepository;
import com.example.restoranvoting.repository.UserRepository;
import com.example.restoranvoting.repository.VoteRepository;
import com.example.restoranvoting.util.validation.ValidationUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.example.restoranvoting.util.validation.ValidationUtil.checkTime;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteRestController {
    static final String REST_URL = "/api/profile";

    @Autowired
    UserRepository userRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    private VoteRepository voteRepository;

    @GetMapping("/{user_id}/votes/{restaurant_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vote(@PathVariable int user_id, @PathVariable int restaurant_id) {
        log.info("vote {} for restaurant {}", user_id, restaurant_id);
        Vote vote = new Vote(userRepository.getById(user_id),
                restaurantRepository.getById(restaurant_id),
                true,
                LocalDateTime.now());
        voteRepository.save(vote);
    }

    @SneakyThrows
    @GetMapping("/{user_id}/unvotes/{restaurant_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unVote(@PathVariable int user_id , @PathVariable int restaurant_id) {
        log.info("unVote {} for restaurant {}", user_id, restaurant_id);
        if (checkTime()) {
            Vote vote = voteRepository.findByUser_IdAndRestaurant_Id(user_id, restaurant_id);
            vote.setVote(false);
            voteRepository.save(vote);
        } else {
            throw new Exception("It is already 11!");
        }

    }
}

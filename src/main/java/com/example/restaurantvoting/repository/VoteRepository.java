package com.example.restaurantvoting.repository;

import com.example.restaurantvoting.model.Restaurant;
import com.example.restaurantvoting.model.Vote;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface VoteRepository extends BaseRepository<Vote> {

    @Modifying
    @Query("update Vote v set v.restaurant = ?1 where v.id = ?2")
    void setRestaurant(Restaurant restaurant_id, int user_id);

    boolean existsByUserId(int user_id);

    Vote findByUserId(Integer user_id);
}

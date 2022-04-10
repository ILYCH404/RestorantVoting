package com.example.restoranvoting.repository;

import com.example.restoranvoting.model.Restaurant;
import com.example.restoranvoting.model.Vote;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface VoteRepository extends BaseRepository<Vote> {

    @Modifying
    @Query("update Vote v set v.restaurant = ?1 where v.id = ?2")
    void setRestaurant(Restaurant restaurant_id, int user_id);

    boolean existsByUserId(int user_id);
}

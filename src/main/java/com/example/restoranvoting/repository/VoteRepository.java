package com.example.restoranvoting.repository;

import com.example.restoranvoting.model.Vote;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface VoteRepository extends BaseRepository<Vote>{

    Vote findByUser_IdAndRestaurant_Id(int user_id, int restaurant_id);
}

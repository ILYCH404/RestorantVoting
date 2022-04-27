package com.example.restaurantvoting;

import com.example.restaurantvoting.model.Vote;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static com.example.restaurantvoting.RestaurantTestData.restaurant2;
import static com.example.restaurantvoting.RestaurantTestData.restaurant3;
import static com.example.restaurantvoting.UserTestData.admin;


public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user");

    public static final int VOTE1_ID = 1;

    public static Vote getNew() {
        return new Vote(null, admin, restaurant2, LocalTime.now().truncatedTo(ChronoUnit.MINUTES));
    }

    public static Vote getUpdated() {
        return new Vote(VOTE1_ID, admin, restaurant3, LocalTime.now().truncatedTo(ChronoUnit.MINUTES));
    }
}

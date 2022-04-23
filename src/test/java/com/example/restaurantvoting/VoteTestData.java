package com.example.restaurantvoting;

import com.example.restaurantvoting.model.Restaurant;
import com.example.restaurantvoting.model.User;
import com.example.restaurantvoting.model.Vote;
import org.hibernate.Hibernate;

import static com.example.restaurantvoting.RestaurantTestData.restaurant1;
import static com.example.restaurantvoting.UserTestData.user;


public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "DateTime", "user","restaurant");

    public static final int VOTE1_ID = 1;

    public static final Vote vote1 = new Vote(VOTE1_ID , (User) Hibernate.unproxy(user), (Restaurant) Hibernate.unproxy(restaurant1));
}

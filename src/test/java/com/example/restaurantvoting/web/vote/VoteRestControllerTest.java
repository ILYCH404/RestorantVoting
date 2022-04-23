package com.example.restaurantvoting.web.vote;

import com.example.restaurantvoting.model.Vote;
import com.example.restaurantvoting.repository.VoteRepository;
import com.example.restaurantvoting.web.AbstractControllerTest;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.restaurantvoting.RestaurantTestData.RESTAURANT1_ID;
import static com.example.restaurantvoting.UserTestData.ADMIN_MAIL;
import static com.example.restaurantvoting.UserTestData.USER_ID;
import static com.example.restaurantvoting.VoteTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

class VoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VoteRestController.REST_URL + '/';
    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void vote() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + USER_ID +"/votes/" + RESTAURANT1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        VOTE_MATCHER.assertMatch((Vote) Hibernate.unproxy(voteRepository.getById(VOTE1_ID)), vote1);
    }
}
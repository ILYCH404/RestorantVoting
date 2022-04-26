package com.example.restaurantvoting.web.vote;

import com.example.restaurantvoting.VoteTestData;
import com.example.restaurantvoting.model.Vote;
import com.example.restaurantvoting.repository.VoteRepository;
import com.example.restaurantvoting.util.JsonUtil;
import com.example.restaurantvoting.web.AbstractControllerTest;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.restaurantvoting.RestaurantTestData.RESTAURANT1_ID;
import static com.example.restaurantvoting.UserTestData.*;
import static com.example.restaurantvoting.VoteTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

class VoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VoteRestController.REST_URL + '/';
    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void vote() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + USER_ID +"/votes/" + RESTAURANT1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        VOTE_MATCHER.assertMatch((Vote) Hibernate.unproxy(voteRepository.getById(VOTE1_ID)), vote1);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void voteAfterElevenClock() throws Exception {
        Vote updated = VoteTestData.getUpdated();
        perform(MockMvcRequestBuilders.post(REST_URL + USER_ID + "/votes/" + RESTAURANT1_ID + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity());
    }
}
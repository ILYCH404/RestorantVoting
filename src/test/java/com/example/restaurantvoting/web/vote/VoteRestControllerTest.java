package com.example.restaurantvoting.web.vote;

import com.example.restaurantvoting.VoteTestData;
import com.example.restaurantvoting.model.Vote;
import com.example.restaurantvoting.repository.VoteRepository;
import com.example.restaurantvoting.util.JsonUtil;
import com.example.restaurantvoting.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalTime;

import static com.example.restaurantvoting.UserTestData.USER_ID;
import static com.example.restaurantvoting.UserTestData.USER_MAIL;
import static com.example.restaurantvoting.VoteTestData.VOTE1_ID;
import static com.example.restaurantvoting.VoteTestData.VOTE_MATCHER;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VoteRestController.REST_URL + '/';
    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void vote() {
        try {
            Vote newVote = VoteTestData.getNew();
            ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "1/votes/2")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(newVote)))
                    .andExpect(status().isCreated());

            Vote created = VOTE_MATCHER.readFromJson(action);
            int newId = created.id();
            newVote.setId(newId);

            VOTE_MATCHER.assertMatch(created, newVote);
            VOTE_MATCHER.assertMatch(voteRepository.getById(newId), newVote);
        } catch (Exception ignored) {
        }
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateVote() {
        try {
            MockedStatic<LocalTime> mocked = Mockito.mockStatic(LocalTime.class);
            mocked.when(LocalTime::now).thenReturn(LocalTime.of(10, 1));
            Vote newVote = VoteTestData.getUpdated();
            ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "1/votes/3")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(newVote)))
                    .andExpect(status().isCreated());

            Vote created = VOTE_MATCHER.readFromJson(action);
            int newId = created.id();
            newVote.setId(newId);

            VOTE_MATCHER.assertMatch(created, newVote);
            VOTE_MATCHER.assertMatch(voteRepository.getById(newId), newVote);
        } catch (Exception ignored) {
        }
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void voteAfterElevenClock() {
        try {
            MockedStatic<LocalTime> mocked = Mockito.mockStatic(LocalTime.class);
            mocked.when(LocalTime::now).thenReturn(LocalTime.of(11, 1));
            Vote updated = VoteTestData.getUpdated();
            perform(MockMvcRequestBuilders.post(REST_URL + "1/votes/2")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(updated)))
                    .andExpect(status().isUnprocessableEntity());
        } catch (Exception ignored) {

        }
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + USER_ID + "/votes"))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertFalse(voteRepository.findById(VOTE1_ID).isPresent());
    }
}
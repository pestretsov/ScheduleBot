package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.schedulebot.model.UserRoute;
import org.anstreth.schedulebot.response.BotResponse;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulerrepository.UserRouteRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class UserRouteInitializerTest {
    @InjectMocks
    private UserRouteInitializer initializer;

    @Mock
    private UserRouteRepository userRouteRepository;

    @Test
    public void initializer_puts_GROUP_SEARCH_route_to_repository_and_asks_for_group() throws Exception {
        long userId = 1;
        UserRequest userRequest = new UserRequest(userId, "msg");
        BotResponse askForGroupResponse = new BotResponse("Send me your group number like '12345/6' to get your schedule.");

        assertThat(initializer.handleRequest(userRequest), is(askForGroupResponse));

        then(userRouteRepository).should().save(userId, UserRoute.GROUP_SEARCH);
    }
}
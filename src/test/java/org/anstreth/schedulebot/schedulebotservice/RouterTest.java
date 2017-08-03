package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.schedulebot.model.UserRoute;
import org.anstreth.schedulebot.response.BotResponse;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulerrepository.UserRouteRepository;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class RouterTest {
    @InjectMocks
    private Router router;
    @Mock
    private UserRouteRepository routeRepository;
    @Mock
    private GroupSearchService groupSearcher;

    @Test
    public void when_user_route_is_null_then_his_route_is_set_to_group_search_and_request_passed_to_group_search_service() throws Exception {
        long userId = 1;
        UserRequest userRequest = new UserRequest(userId, "command");
        doReturn(null).when(routeRepository).get(userId);
        BotResponse responseFromSearcher = mock(BotResponse.class);
        doReturn(responseFromSearcher).when(groupSearcher).handleRequest(userRequest);

        assertThat(router.route(userRequest), is(responseFromSearcher));

        then(routeRepository).should().save(userId, UserRoute.GROUP_SEARCH);
    }

}

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

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class UserRequestRouterTest {
    @InjectMocks
    private UserRequestRouter router;
    @Mock
    private UserRouteRepository routeRepository;
    @Mock
    private GroupSearchService groupSearcher;
    @Mock
    private SchedulerBotMenu menuService;
    @Mock
    private SchedulerBotHome homeService;
    @Mock
    private UserRouteInitializer initService;

    @Test
    public void when_user_route_is_null_request_passed_to_user_init_service() throws Exception {
        long userId = 1;
        UserRequest userRequest = new UserRequest(userId, "command");
        doReturn(null).when(routeRepository).get(userId);
        BotResponse responseFromInit = mock(BotResponse.class);
        doReturn(responseFromInit).when(initService).handleRequest(userRequest);

        assertThat(router.route(userRequest), is(responseFromInit));
    }

    @Test
    public void when_user_route_is_group_search_request_passed_to_group_search_service() throws Exception {
        long userId = 1;
        UserRequest userRequest = new UserRequest(userId, "command");
        doReturn(UserRoute.GROUP_SEARCH).when(routeRepository).get(userId);
        BotResponse responseFromSearcher = mock(BotResponse.class);
        doReturn(responseFromSearcher).when(groupSearcher).handleRequest(userRequest);

        assertThat(router.route(userRequest), is(responseFromSearcher));
    }

    @Test
    public void when_user_route_is_menu_request_is_passed_to_menu_service() throws Exception {
        long userId = 1;
        UserRequest userRequest = new UserRequest(userId, "command");
        doReturn(UserRoute.MENU).when(routeRepository).get(userId);
        BotResponse responseFromMenu = mock(BotResponse.class);
        doReturn(responseFromMenu).when(menuService).handleRequest(userRequest);

        assertThat(router.route(userRequest), is(responseFromMenu));
    }

    @Test
    public void when_user_route_is_home_request_is_passed_to_home_service() throws Exception {
        long userId = 1;
        UserRequest userRequest = new UserRequest(userId, "command");
        doReturn(UserRoute.HOME).when(routeRepository).get(userId);
        BotResponse responseFromHome = mock(BotResponse.class);
        doReturn(responseFromHome).when(homeService).handleRequest(userRequest);

        assertThat(router.route(userRequest), is(responseFromHome));
    }
}

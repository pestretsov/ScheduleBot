package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.schedulebot.model.UserRoute;
import org.anstreth.schedulebot.response.BotResponse;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulerrepository.UserRouteRepository;

public class Router {
    private final UserRouteRepository userRouteRepository;
    private final GroupSearchService groupSearchService;
    private final SchedulerBotMenu schedulerBotMenu;
    private final SchedulerBotHome schedulerBotHome;

    public Router(UserRouteRepository userRouteRepository, GroupSearchService groupSearchService, SchedulerBotMenu schedulerBotMenu, SchedulerBotHome schedulerBotHome) {
        this.userRouteRepository = userRouteRepository;
        this.groupSearchService = groupSearchService;
        this.schedulerBotMenu = schedulerBotMenu;
        this.schedulerBotHome = schedulerBotHome;
    }

    BotResponse route(UserRequest userRequest) {
        UserRoute userRoute = getUserRoute(userRequest.getUserId());

        switch (userRoute) {
            case GROUP_SEARCH:
                return groupSearchService.handleRequest(userRequest);
            case MENU:
                return schedulerBotMenu.handleRequest(userRequest);
            case HOME:
                return schedulerBotHome.handleRequest(userRequest);
        }

        throw new IllegalArgumentException("Illegal user route " + userRoute);
    }

    private UserRoute getUserRoute(long userId) {
        UserRoute userRoute = userRouteRepository.get(userId);
        if (userRoute != null) {
            return userRoute;
        }
        userRouteRepository.save(userId, UserRoute.GROUP_SEARCH);
        return UserRoute.GROUP_SEARCH;
    }
}

interface GroupSearchService {
    BotResponse handleRequest(UserRequest userRequest);
}

interface SchedulerBotHome {
    BotResponse handleRequest(UserRequest userRequest);
}

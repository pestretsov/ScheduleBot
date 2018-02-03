package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.schedulebot.model.UserRoute;
import org.anstreth.schedulebot.response.BotResponse;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulerrepository.UserRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class UserRequestRouter {
    private final UserRouteRepository userRouteRepository;
    private final UserRouteInitializer userRouteInitializer;
    private final GroupSearchService groupSearchService;
    private final SchedulerBotMenu schedulerBotMenu;
    private final SchedulerBotHome schedulerBotHome;

    @Autowired
    public UserRequestRouter(
            UserRouteRepository userRouteRepository,
            UserRouteInitializer userRouteInitializer,
            GroupSearchService groupSearchService,
            SchedulerBotMenu schedulerBotMenu,
            SchedulerBotHome schedulerBotHome
    ) {
        this.userRouteRepository = userRouteRepository;
        this.userRouteInitializer = userRouteInitializer;
        this.groupSearchService = groupSearchService;
        this.schedulerBotMenu = schedulerBotMenu;
        this.schedulerBotHome = schedulerBotHome;
    }

    @Async
    public CompletableFuture<BotResponse> routeAsync(UserRequest userRequest) {
        return CompletableFuture.completedFuture(route(userRequest));
    }

    BotResponse route(UserRequest userRequest) {
        UserRoute userRoute = userRouteRepository.get(userRequest.getUserId());
        if (userRoute == null) {
            return userRouteInitializer.handleRequest(userRequest);
        }

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
}
package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.schedulebot.model.UserRoute;
import org.anstreth.schedulebot.response.BotResponse;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulerrepository.UserRouteRepository;

public class Router {
    private final UserRouteRepository userRouteRepository;
    private final GroupSearchService groupSearchService;

    public Router(UserRouteRepository userRouteRepository, GroupSearchService groupSearchService) {
        this.userRouteRepository = userRouteRepository;
        this.groupSearchService = groupSearchService;
    }

    BotResponse route(UserRequest userRequest) {
        UserRoute userRoute = userRouteRepository.get(userRequest.getUserId());
        if (userRoute == null) {
            userRoute = UserRoute.GROUP_SEARCH;
            userRouteRepository.save(userRequest.getUserId(), UserRoute.GROUP_SEARCH);
        }

        if (userRoute == UserRoute.GROUP_SEARCH) {
            return groupSearchService.handleRequest(userRequest);
        }

        return null;
    }
}

interface GroupSearchService {
    BotResponse handleRequest(UserRequest userRequest);
}

package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.schedulebot.model.UserRoute;
import org.anstreth.schedulebot.response.BotResponse;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulerrepository.UserRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;

class UserRouteInitializer {

    private static final BotResponse askForGroup
            = new BotResponse("Send me your group number like '12345/6' to get your schedule.");
    private final UserRouteRepository userRouteRepository;

    UserRouteInitializer(UserRouteRepository userRouteRepository) {
        this.userRouteRepository = userRouteRepository;
    }

    BotResponse handleRequest(UserRequest userRequest) {
        userRouteRepository.save(userRequest.getUserId(), UserRoute.GROUP_SEARCH);
        return askForGroup;
    }
}

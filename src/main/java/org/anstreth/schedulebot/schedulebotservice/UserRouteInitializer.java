package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.schedulebot.model.UserRoute;
import org.anstreth.schedulebot.response.BotResponse;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulerrepository.UserRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class UserRouteInitializer {

    private static final BotResponse ASK_FOR_GROUP_USER_CHAT
            = new BotResponse("Send me your group number like '12345/6' to get your schedule.");

    private static final BotResponse ASK_FOR_GROUP_GROUP_CHAT
            = new BotResponse("Send me your group number like '/set_group 12345/6' to get your schedule.");

    private final UserRouteRepository userRouteRepository;

    @Autowired
    UserRouteInitializer(UserRouteRepository userRouteRepository) {
        this.userRouteRepository = userRouteRepository;
    }

    BotResponse handleRequest(UserRequest userRequest) {
        userRouteRepository.save(userRequest.getUserId(), UserRoute.GROUP_SEARCH);
        return userRequest.isGroupChat() ? ASK_FOR_GROUP_GROUP_CHAT : ASK_FOR_GROUP_USER_CHAT;
    }
}

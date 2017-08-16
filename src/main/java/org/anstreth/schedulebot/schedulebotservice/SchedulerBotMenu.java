package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.schedulebot.commands.MenuCommandParser;
import org.anstreth.schedulebot.model.UserRoute;
import org.anstreth.schedulebot.response.BotResponse;
import org.anstreth.schedulebot.response.PossibleReplies;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulebotservice.user.UserStateManager;
import org.anstreth.schedulebot.schedulerrepository.UserGroupRepository;
import org.anstreth.schedulebot.schedulerrepository.UserRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class SchedulerBotMenu {
    private final UserStateManager userStateManager;
    private final MenuCommandParser menuCommandsParser;
    private final UserGroupRepository userGroupRepository;
    private final UserRouteRepository userRouteRepository;

    @Autowired
    SchedulerBotMenu(
            UserStateManager userStateManager,
            UserGroupRepository userGroupRepository,
            MenuCommandParser menuCommandsParser, UserRouteRepository userRouteRepository) {
        this.userStateManager = userStateManager;
        this.menuCommandsParser = menuCommandsParser;
        this.userGroupRepository = userGroupRepository;
        this.userRouteRepository = userRouteRepository;
    }

    BotResponse handleRequest(UserRequest request) {
        switch (menuCommandsParser.parse(request.getMessage())) {
            case BACK:
                userStateManager.transitToWithGroup(request.getUserId());
                userRouteRepository.save(request.getUserId(), UserRoute.HOME);
                return new BotResponse(
                        "You can ask for schedule now.",
                        PossibleReplies.WITH_GROUP_REPLIES
                );

            case RESET_GROUP:
                removeUsersGroup(request.getUserId());
                userRouteRepository.save(request.getUserId(), UserRoute.GROUP_SEARCH);
                return new BotResponse("Send me your group number like '12345/6' to get your schedule.");

            default:
                return new BotResponse(
                        "Sorry, don't understand that!",
                        PossibleReplies.MENU_REPLIES
                );
        }
    }

    private void removeUsersGroup(long userId) {
        userStateManager.transitToAskedForGroup(userId);
        userGroupRepository.remove(userId);
    }
}

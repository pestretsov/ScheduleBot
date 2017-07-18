package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.schedulebot.commands.MenuCommandParser;
import org.anstreth.schedulebot.response.BotResponse;
import org.anstreth.schedulebot.response.PossibleReplies;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulebotservice.user.UserStateManager;
import org.anstreth.schedulebot.schedulerrepository.UserGroupRepository;
import org.springframework.stereotype.Component;

@Component
class SchedulerBotMenu {
    private final UserStateManager userStateManager;
    private final MenuCommandParser menuCommandsParser;
    private final UserGroupRepository userGroupRepository;

    SchedulerBotMenu(UserStateManager userStateManager, UserGroupRepository userGroupRepository, MenuCommandParser menuCommandsParser) {
        this.userStateManager = userStateManager;
        this.menuCommandsParser = menuCommandsParser;
        this.userGroupRepository = userGroupRepository;
    }

    BotResponse handleRequest(UserRequest request) {
        switch (menuCommandsParser.parse(request.getMessage())) {
            case BACK:
                userStateManager.transitToWithGroup(request.getUserId());
                return new BotResponse(
                        "You can ask for schedule now.",
                        PossibleReplies.WITH_GROUP_REPLIES
                );

            case RESET_GROUP:
                userStateManager.transitToAskedForGroup(request.getUserId());
                userGroupRepository.remove(request.getUserId());
                return new BotResponse("Send me your group number like '12345/6' to get your schedule.");

            default:
                return new BotResponse(
                        "Sorry, don't understand that!",
                        PossibleReplies.MENU_REPLIES
                );
        }
    }
}

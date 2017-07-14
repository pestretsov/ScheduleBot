package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.schedulebot.commands.MenuCommandParser;
import org.anstreth.schedulebot.response.BotResponse;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulebotservice.user.UserStateManager;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
class SchedulerBotMenu {
    private final UserStateManager userStateManager;
    private final MenuCommandParser menuCommandsParser;
    private final List<String> scheduleCommands = Arrays.asList("Today", "Tomorrow", "Week");
    private final List<String> menuCommands = Arrays.asList("Reset group", "Back");

    SchedulerBotMenu(UserStateManager userStateManager, MenuCommandParser menuCommandsParser) {
        this.userStateManager = userStateManager;
        this.menuCommandsParser = menuCommandsParser;
    }

    BotResponse handleRequest(UserRequest request) {
        switch (menuCommandsParser.parse(request.getMessage())) {
            case BACK:
                userStateManager.transitToWithGroup(request.getUserId());
                return new BotResponse("You can ask for schedule now.", scheduleCommands);

            case RESET_GROUP:
                userStateManager.transitToAskedForGroup(request.getUserId());
                return new BotResponse("Send me your group number like '12345/6' to get your schedule.");

            default:
                return new BotResponse("Sorry, don't understand that!", menuCommands);
        }
    }
}

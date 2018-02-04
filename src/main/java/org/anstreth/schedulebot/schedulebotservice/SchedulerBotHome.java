package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.schedulebot.commands.FullCommand;
import org.anstreth.schedulebot.commands.UserCommand;
import org.anstreth.schedulebot.commands.UserCommandParser;
import org.anstreth.schedulebot.response.BotResponse;
import org.anstreth.schedulebot.response.PossibleReplies;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulerbotcommandshandler.SchedulerBotCommandsHandler;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerrepository.UserGroupRepository;
import org.anstreth.schedulebot.schedulerrepository.UserRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.anstreth.schedulebot.model.UserRoute.GROUP_SEARCH;
import static org.anstreth.schedulebot.model.UserRoute.MENU;

@Component
class SchedulerBotHome {
    private static final BotResponse MENU_ROUTE_RESPONSE
        = new BotResponse("What do you want to do?", PossibleReplies.MENU_REPLIES);
    private final UserRouteRepository userRouteRepository;
    private final UserCommandParser userCommandParser;
    private final SchedulerBotCommandsHandler commandsHandler;
    private final UserGroupRepository userGroupRepository;
    private final GroupSearchService groupSearchService;

    @Autowired
    SchedulerBotHome(
            UserRouteRepository userRouteRepository,
            UserCommandParser userCommandParser,
            SchedulerBotCommandsHandler commandsHandler,
            UserGroupRepository userGroupRepository,
            GroupSearchService groupSearchService
    ) {
        this.userRouteRepository = userRouteRepository;
        this.userCommandParser = userCommandParser;
        this.commandsHandler = commandsHandler;
        this.userGroupRepository = userGroupRepository;
        this.groupSearchService = groupSearchService;
    }

    BotResponse handleRequest(UserRequest userRequest) {
        FullCommand<UserCommand> command = userCommandParser.parse(userRequest.getMessage());
        switch (command.getName()) {
            case MENU:
                routeUserToMenu(userRequest);
                return MENU_ROUTE_RESPONSE;
            case SET_GROUP:
                deleteUserGroup(userRequest);
                routeUserToGroupSearch(userRequest);
                return groupSearchService.handleRequest(userRequest);
            default:
                return handleUserScheduleCommand(userRequest.getUserId(), command.getName());
        }
    }

    private BotResponse handleUserScheduleCommand(long userId, UserCommand command) {
        int userGroup = userGroupRepository.get(userId);
        ScheduleRequest scheduleRequest = new ScheduleRequest(userGroup, command);
        List<String> scheduleResponses = commandsHandler.handleRequest(scheduleRequest);
        return new BotResponse(scheduleResponses, PossibleReplies.WITH_GROUP_REPLIES);
    }

    private void deleteUserGroup(UserRequest request) {
        userGroupRepository.remove(request.getUserId());
    }

    private void routeUserToGroupSearch(UserRequest request) {
        userRouteRepository.save(request.getUserId(), GROUP_SEARCH);
    }

    private void routeUserToMenu(UserRequest request) {
        userRouteRepository.save(request.getUserId(), MENU);
    }

}

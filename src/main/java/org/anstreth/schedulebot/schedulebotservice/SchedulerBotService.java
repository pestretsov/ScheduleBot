package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.schedulebot.commands.ScheduleCommand;
import org.anstreth.schedulebot.commands.ScheduleCommandParser;
import org.anstreth.schedulebot.exceptions.NoGroupForUserException;
import org.anstreth.schedulebot.exceptions.NoSuchUserException;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulerbotcommandshandler.SchedulerBotCommandsHandler;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SchedulerBotService {
    private final UserGroupManager userGroupManager;
    private final SchedulerBotCommandsHandler schedulerBotCommandsHandler;
    private final ScheduleCommandParser scheduleCommandParser;
    private final UserGroupSearchService userGroupSearcherService;
    private final UserCreationService userCreationService;

    private final List<String> possibleReplies = Arrays.asList("Today", "Tomorrow", "Week");

    @Autowired
    public SchedulerBotService(UserGroupManager userGroupManager, UserGroupSearchService userGroupSearcherService, SchedulerBotCommandsHandler schedulerBotCommandsHandler, ScheduleCommandParser scheduleCommandParser, UserCreationService userCreationService) {
        this.userGroupManager = userGroupManager;
        this.schedulerBotCommandsHandler = schedulerBotCommandsHandler;
        this.scheduleCommandParser = scheduleCommandParser;
        this.userGroupSearcherService = userGroupSearcherService;
        this.userCreationService = userCreationService;
    }

    @Async
    public void handleRequest(UserRequest userRequest, MessageWithRepliesSender messageSender) {
        try {
            handleUserCommand(userRequest, messageSender);
        } catch (NoSuchUserException e) {
            createUserAndAskForGroup(userRequest, messageSender);
        } catch (NoGroupForUserException e) {
            tryToFindUserGroup(userRequest, messageSender);
        }
    }

    private void handleUserCommand(UserRequest userRequest, MessageWithRepliesSender messageSender) {
        int id = userGroupManager.getGroupIdOfUser(userRequest.getUserId());
        ScheduleCommand command = getCommand(userRequest);
        ScheduleRequest scheduleRequest = new ScheduleRequest(id, command);
        MessageSender withReplies = messageSender.withReplies(possibleReplies);
        List<String> scheduleMessages = schedulerBotCommandsHandler.handleRequestWithResponse(scheduleRequest);
        scheduleMessages.forEach(withReplies::sendMessage);
    }

    private void createUserAndAskForGroup(UserRequest userRequest, MessageWithRepliesSender messageSender) {
        userCreationService.createUserAndAskForGroup(userRequest, messageSender);
    }

    private void tryToFindUserGroup(UserRequest userRequest, MessageWithRepliesSender messageSender) {
        userGroupSearcherService.tryToFindUserGroup(userRequest, messageSender, possibleReplies);
    }

    private ScheduleCommand getCommand(UserRequest userRequest) {
        return scheduleCommandParser.parse(userRequest.getMessage());
    }

}

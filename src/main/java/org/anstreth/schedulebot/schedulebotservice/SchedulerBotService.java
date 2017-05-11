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

    private final List<String> possibleReplies = Arrays.asList("Today", "Tomorrow", "Week");


    @Autowired
    public SchedulerBotService(UserGroupManager userGroupManager, UserGroupSearchService userGroupSearcherService, SchedulerBotCommandsHandler schedulerBotCommandsHandler, ScheduleCommandParser scheduleCommandParser) {
        this.userGroupManager = userGroupManager;
        this.schedulerBotCommandsHandler = schedulerBotCommandsHandler;
        this.scheduleCommandParser = scheduleCommandParser;
        this.userGroupSearcherService = userGroupSearcherService;
    }

    @Async
    public void handleRequest(UserRequest userRequest, MessageWithRepliesSender messageSender) {
        try {
            findUserAndScheduleForHisGroup(userRequest, messageSender);
        } catch (NoGroupForUserException e) {
            userGroupManager.handleUserAbsense(userRequest, messageSender);
        }
    }

    void otherHandleRequest(UserRequest userRequest, MessageWithRepliesSender messageSender) {
        try {
            handleUserCommand(userRequest, messageSender);
        } catch (NoSuchUserException e) {
            createUser(userRequest);
            askForGroup(messageSender);
        } catch (NoGroupForUserException e) {
            tryToFindUserGroup(userRequest, messageSender);
        }
    }

    private void askForGroup(MessageWithRepliesSender messageSender) {
        messageSender.sendMessage("Send me your group number like '12345/6' to get your schedule.");
    }

    private void createUser(UserRequest userRequest) {
        userGroupManager.saveUserWithoutGroup(userRequest.getUserId());
    }

    private void handleUserCommand(UserRequest userRequest, MessageWithRepliesSender messageSender) {
        int id = userGroupManager.getGroupIdOfUserWithExceptions(userRequest.getUserId());
        ScheduleCommand command = getCommand(userRequest);
        ScheduleRequest scheduleRequest = new ScheduleRequest(id, command);
        schedulerBotCommandsHandler.handleRequest(
                scheduleRequest,
                messageSender.withReplies(possibleReplies)
        );
    }

    private void tryToFindUserGroup(UserRequest userRequest, MessageWithRepliesSender messageSender) {
        userGroupSearcherService.tryToFindUserGroup(userRequest, messageSender, possibleReplies);
    }

    private void findUserAndScheduleForHisGroup(UserRequest userRequest, MessageWithRepliesSender messageSender) {
        int id = getUserGroupId(userRequest);
        ScheduleCommand command = getCommand(userRequest);
        ScheduleRequest scheduleRequest = new ScheduleRequest(id, command);
        schedulerBotCommandsHandler.handleRequest(
                scheduleRequest,
                messageSender.withReplies(possibleReplies)
        );
    }

    private int getUserGroupId(UserRequest userRequest) {
        return userGroupManager.getGroupIdOfUser(userRequest.getUserId())
                .orElseThrow(NoGroupForUserException::new);
    }

    private ScheduleCommand getCommand(UserRequest userRequest) {
        return scheduleCommandParser.parse(userRequest.getMessage());
    }

}

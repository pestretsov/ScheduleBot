package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.schedulebot.commands.ScheduleCommand;
import org.anstreth.schedulebot.commands.ScheduleCommandParser;
import org.anstreth.schedulebot.model.User;
import org.anstreth.schedulebot.response.BotResponse;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulebotservice.user.UserCreationService;
import org.anstreth.schedulebot.schedulebotservice.user.UserGroupManager;
import org.anstreth.schedulebot.schedulebotservice.user.UserGroupSearchService;
import org.anstreth.schedulebot.schedulebotservice.user.UserStateManager;
import org.anstreth.schedulebot.schedulerbotcommandshandler.SchedulerBotCommandsHandler;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerrepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class SchedulerBotService {
    private final UserGroupManager userGroupManager;
    private final SchedulerBotCommandsHandler schedulerBotCommandsHandler;
    private final ScheduleCommandParser scheduleCommandParser;
    private final UserGroupSearchService userGroupSearcherService;
    private final UserCreationService userCreationService;
    private final UserStateManager userStateManager;
    private final UserRepository userRepository;

    private final List<String> possibleReplies = Arrays.asList("Today", "Tomorrow", "Week");

    @Autowired
    public SchedulerBotService(UserGroupManager userGroupManager, UserGroupSearchService userGroupSearcherService,
                               SchedulerBotCommandsHandler schedulerBotCommandsHandler, ScheduleCommandParser scheduleCommandParser, UserCreationService userCreationService,
                               UserStateManager userStateManager, UserRepository userRepository) {
        this.userGroupManager = userGroupManager;
        this.schedulerBotCommandsHandler = schedulerBotCommandsHandler;
        this.scheduleCommandParser = scheduleCommandParser;
        this.userGroupSearcherService = userGroupSearcherService;
        this.userCreationService = userCreationService;
        this.userStateManager = userStateManager;
        this.userRepository = userRepository;
    }

    @Async
    public CompletableFuture<BotResponse> handleRequestAsync(UserRequest userRequest) {
        return CompletableFuture.completedFuture(handleRequest(userRequest));
    }

    BotResponse handleRequest(UserRequest userRequest) {
        User user = userRepository.getUserById(userRequest.getUserId());

        if (user == null) {
            return createUserAndAskForGroup(userRequest);
        }

        switch (user.getState()) {
            case ASKED_FOR_GROUP:
                return tryToFindUserGroup(userRequest);
            case WITH_GROUP:
                return handleUserCommand(userRequest);
        }

        return null;
//        try {
//            return handleUserCommand(userRequest);
//        } catch (NoSuchUserException e) {
//            return createUserAndAskForGroup(userRequest);
//        } catch (NoGroupForUserException e) {
//            return tryToFindUserGroup(userRequest);
//        }
    }

    private BotResponse handleUserCommand(UserRequest userRequest) {
        List<String> scheduleMessages = getScheduleReplies(userRequest);
        return new BotResponse(scheduleMessages, possibleReplies);
    }

    private List<String> getScheduleReplies(UserRequest userRequest) {
        int id = userRepository.getUserById(userRequest.getUserId()).getGroupId();
        ScheduleCommand command = getCommand(userRequest);
        ScheduleRequest scheduleRequest = new ScheduleRequest(id, command);
        return schedulerBotCommandsHandler.handleRequest(scheduleRequest);
    }

    private BotResponse createUserAndAskForGroup(UserRequest userRequest) {
        userCreationService.createNewUser(userRequest);
        userStateManager.transitToAskedForGroup(userRequest.getUserId());
        return getAskForGroupResponse();
    }

    private BotResponse getAskForGroupResponse() {
        return new BotResponse("Send me your group number like '12345/6' to get your schedule.");
    }

    private BotResponse tryToFindUserGroup(UserRequest userRequest) {
        return userGroupSearcherService.tryToFindUserGroup(userRequest, possibleReplies);
    }

    private ScheduleCommand getCommand(UserRequest userRequest) {
        return scheduleCommandParser.parse(userRequest.getMessage());
    }

}

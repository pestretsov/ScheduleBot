package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.ruzapi.response.Group;
import org.anstreth.schedulebot.commands.ScheduleCommand;
import org.anstreth.schedulebot.commands.ScheduleCommandParser;
import org.anstreth.schedulebot.model.User;
import org.anstreth.schedulebot.model.UserState;
import org.anstreth.schedulebot.response.BotResponse;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulebotservice.user.UserCreationService;
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
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class SchedulerBotService {
    private final SchedulerBotCommandsHandler schedulerBotCommandsHandler;
    private final ScheduleCommandParser scheduleCommandParser;
    private final UserCreationService userCreationService;
    private final UserStateManager userStateManager;
    private final UserRepository userRepository;

    private final List<String> possibleReplies = Arrays.asList("Today", "Tomorrow", "Week");
    private final GroupManager groupManager;

    @Autowired
    public SchedulerBotService(UserGroupSearchService userGroupSearcherService,
                               SchedulerBotCommandsHandler schedulerBotCommandsHandler, ScheduleCommandParser scheduleCommandParser, UserCreationService userCreationService,
                               UserStateManager userStateManager, UserRepository userRepository, GroupManager groupManager) {
        this.schedulerBotCommandsHandler = schedulerBotCommandsHandler;
        this.scheduleCommandParser = scheduleCommandParser;
        this.userCreationService = userCreationService;
        this.userStateManager = userStateManager;
        this.userRepository = userRepository;
        this.groupManager = groupManager;
    }

    @Async
    public CompletableFuture<BotResponse> handleRequestAsync(UserRequest userRequest) {
        return CompletableFuture.completedFuture(handleRequest(userRequest));
    }

    BotResponse handleRequest(UserRequest userRequest) {
        User user = getUser(userRequest);

        switch (user.getState()) {
            case NO_GROUP:
                userStateManager.transitToAskedForGroup(user);
                return getAskForGroupResponse();
            case ASKED_FOR_GROUP:
                Optional<Group> foundGroup = groupManager.findGroupByName(userRequest.getMessage());
                if (foundGroup.isPresent()) {
                    updateUserGroup(user, foundGroup.get());
                    return groupIsFoundBotResponse(foundGroup.get());
                } else {
                    return groupNotFoundBotResponse(userRequest);
                }
            case WITH_GROUP:
                return handleUserCommand(userRequest);
        }

        return null;
    }

    private User getUser(UserRequest userRequest) {
        User user = userRepository.getUserById(userRequest.getUserId());

        if (user == null) {
            user = userCreationService.createNewUser(userRequest);
        }

        return user;
    }

    private void updateUserGroup(User user, Group group) {
        userRepository.save(new User(user.getId(), group.getId(), UserState.WITH_GROUP));
    }

    private BotResponse groupIsFoundBotResponse(Group group) {
        return new BotResponse(
                String.format("Your group is set to '%s'.", group.getName()),
                possibleReplies
        );
    }

    private BotResponse groupNotFoundBotResponse(UserRequest userRequest) {
        return new BotResponse(String.format("No group by name '%s' is found! Try again.", userRequest.getMessage()));
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

    private BotResponse getAskForGroupResponse() {
        return new BotResponse("Send me your group number like '12345/6' to get your schedule.");
    }

    private ScheduleCommand getCommand(UserRequest userRequest) {
        return scheduleCommandParser.parse(userRequest.getMessage());
    }

}

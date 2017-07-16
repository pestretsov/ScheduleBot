package org.anstreth.schedulebot.schedulebotservice;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.anstreth.ruzapi.response.Group;
import org.anstreth.schedulebot.commands.UserCommand;
import org.anstreth.schedulebot.commands.UserCommandParser;
import org.anstreth.schedulebot.model.User;
import org.anstreth.schedulebot.model.UserState;
import org.anstreth.schedulebot.response.BotResponse;
import org.anstreth.schedulebot.response.PossibleReplies;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulebotservice.user.UserCreationService;
import org.anstreth.schedulebot.schedulebotservice.user.UserStateManager;
import org.anstreth.schedulebot.schedulerbotcommandshandler.SchedulerBotCommandsHandler;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerrepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SchedulerBotService {
    private final SchedulerBotCommandsHandler schedulerBotCommandsHandler;
    private final UserCommandParser userCommandParser;
    private final UserCreationService userCreationService;
    private final UserStateManager userStateManager;
    private final UserRepository userRepository;
    private final GroupSearcher groupSearcher;
    private final SchedulerBotMenu scheduleBotMenu;

    @Autowired
    public SchedulerBotService(SchedulerBotCommandsHandler schedulerBotCommandsHandler,
                               UserCommandParser userCommandParser, UserCreationService userCreationService,
                               UserStateManager userStateManager, UserRepository userRepository,
                               GroupSearcher groupSearcher, SchedulerBotMenu scheduleBotMenu) {
        this.schedulerBotCommandsHandler = schedulerBotCommandsHandler;
        this.userCommandParser = userCommandParser;
        this.userCreationService = userCreationService;
        this.userStateManager = userStateManager;
        this.userRepository = userRepository;
        this.groupSearcher = groupSearcher;
        this.scheduleBotMenu = scheduleBotMenu;
    }

    @Async
    public CompletableFuture<BotResponse> handleRequestAsync(UserRequest userRequest) {
        return CompletableFuture.completedFuture(handleRequest(userRequest));
    }

    BotResponse handleRequest(UserRequest userRequest) {
        User user = getUser(userRequest.getUserId());

        switch (user.getState()) {
            case NO_GROUP:
                userStateManager.transitToAskedForGroup(user);
                return getAskForGroupResponse();
            case ASKED_FOR_GROUP:
                return handleGroupSearchRequest(userRequest, user);
            case WITH_GROUP:
                return handleUserCommand(user, getCommand(userRequest));
            case MENU:
                return scheduleBotMenu.handleRequest(userRequest);
        }

        return null;
    }

    private User getUser(long userId) {
        User user = userRepository.getUserById(userId);

        if (user != null) {
            return user;
        }

        return userCreationService.createNewUser(userId);
    }

    private BotResponse handleGroupSearchRequest(UserRequest userRequest, User user) {
        Optional<Group> foundGroup = groupSearcher.findGroupByName(userRequest.getMessage());
        if (foundGroup.isPresent()) {
            updateUserGroup(user, foundGroup.get());
            return groupIsFoundBotResponse(foundGroup.get());
        } else {
            return groupNotFoundBotResponse(userRequest);
        }
    }

    private void updateUserGroup(User user, Group group) {
        userRepository.save(new User(user.getId(), group.getId(), UserState.WITH_GROUP));
    }

    private BotResponse getAskForGroupResponse() {
        return new BotResponse("Send me your group number like '12345/6' to get your schedule.");
    }

    private BotResponse groupIsFoundBotResponse(Group group) {
        String groupIsFoundMessage = String.format("Your group is set to '%s'.", group.getName());
        return new BotResponse(groupIsFoundMessage, PossibleReplies.WITH_GROUP_REPLIES);
    }

    private BotResponse groupNotFoundBotResponse(UserRequest userRequest) {
        return new BotResponse(String.format("No group by name '%s' is found! Try again.", userRequest.getMessage()));
    }

    private BotResponse handleUserCommand(User user, UserCommand command) {
        if (command == UserCommand.MENU) {
            userStateManager.transitToMenu(user.getId());
            return new BotResponse("What do you want to do?", PossibleReplies.MENU_REPLIES);
        } else {
            List<String> scheduleMessages = handleScheduleCommand(user.getGroupId(), command);
            return new BotResponse(scheduleMessages, PossibleReplies.WITH_GROUP_REPLIES);
        }
    }

    private List<String> handleScheduleCommand(int groupId, UserCommand command) {
        ScheduleRequest scheduleRequest = new ScheduleRequest(groupId, command);
        return schedulerBotCommandsHandler.handleRequest(scheduleRequest);
    }

    private UserCommand getCommand(UserRequest userRequest) {
        return userCommandParser.parse(userRequest.getMessage());
    }

}

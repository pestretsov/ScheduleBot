package org.anstreth.schedulebot.scheduleuserservice;

import org.anstreth.schedulebot.model.User;
import org.anstreth.schedulebot.schedulerbotcommandshandler.SchedulerBotCommandsHandler;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerrepository.UserRepository;
import org.anstreth.schedulebot.scheduleuserservice.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchedulerUserService {
    private static final String NO_GROUP_SPECIFIED_MESSAGE = "Send me your group number like '12345/6' to get your schedule.";
    private final SchedulerBotCommandsHandler schedulerBotCommandsHandler;
    private final UserRepository userRepository;

    @Autowired
    public SchedulerUserService(UserRepository userRepository, SchedulerBotCommandsHandler schedulerBotCommandsHandler) {
        this.schedulerBotCommandsHandler = schedulerBotCommandsHandler;
        this.userRepository = userRepository;
    }

    public void handleRequest(UserRequest userRequest, MessageSender messageSender) {
        User user = userRepository.getUserById(userRequest.getUserId());
        if (user == null) {
            createUserWithoutGroup(userRequest.getUserId());
            messageSender.sendMessage(NO_GROUP_SPECIFIED_MESSAGE);
        } else if (user.getGroupId() != User.NO_GROUP_SPECIFIED) {
            schedulerBotCommandsHandler.handleRequest(new ScheduleRequest(user.getGroupId(), userRequest.getMessage()), messageSender);
        }
    }

    private void createUserWithoutGroup(long userId) {
        userRepository.save(new User(userId, User.NO_GROUP_SPECIFIED));
    }
}

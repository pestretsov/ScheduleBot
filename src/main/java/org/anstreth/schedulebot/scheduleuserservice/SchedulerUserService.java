package org.anstreth.schedulebot.scheduleuserservice;

import org.anstreth.schedulebot.model.User;
import org.anstreth.schedulebot.schedulerbotcommandshandler.SchedulerBotCommandsHandler;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerrepository.UserRepository;
import org.anstreth.schedulebot.scheduleuserservice.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SchedulerUserService {
    private final SchedulerBotCommandsHandler schedulerBotCommandsHandler;
    private final UserRepository userRepository;
    private final int myGroupId;

    @Autowired
    public SchedulerUserService(@Value("${my_group.id}") int myGroupId, UserRepository userRepository, SchedulerBotCommandsHandler schedulerBotCommandsHandler) {
        this.schedulerBotCommandsHandler = schedulerBotCommandsHandler;
        this.userRepository = userRepository;
        this.myGroupId = myGroupId;
    }

    public void handleRequest(UserRequest userRequest, MessageSender messageSender) {
        User user = userRepository.getUserById(userRequest.getUserId());
        if (user == null) {
            schedulerBotCommandsHandler.handleRequest(new ScheduleRequest(myGroupId, userRequest.getMessage()), messageSender);
        } else {
            schedulerBotCommandsHandler.handleRequest(new ScheduleRequest(user.getGroupId(), userRequest.getMessage()), messageSender);
        }
    }
}

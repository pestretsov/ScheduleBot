package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulerbotcommandshandler.SchedulerBotCommandsHandler;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SchedulerBotService {
    private final UserManager userManager;
    private final SchedulerBotCommandsHandler schedulerBotCommandsHandler;

    @Autowired
    public SchedulerBotService(UserManager userManager, SchedulerBotCommandsHandler schedulerBotCommandsHandler) {
        this.userManager = userManager;
        this.schedulerBotCommandsHandler = schedulerBotCommandsHandler;
    }

    public void handleRequest(UserRequest userRequest, MessageSender messageSender) {
        Optional<Integer> groupId = userManager.getGroupIdOfUser(userRequest.getUserId());
        if (groupId.isPresent()) {
            ScheduleRequest scheduleRequest = new ScheduleRequest(groupId.get(), userRequest.getMessage());
            schedulerBotCommandsHandler.handleRequest(scheduleRequest, messageSender);
        } else {
            userManager.handleUserAbsense(userRequest, messageSender);
        }
    }
}

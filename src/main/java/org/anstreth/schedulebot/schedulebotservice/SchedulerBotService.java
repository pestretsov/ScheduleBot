package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.schedulebot.exceptions.NoGroupForUserException;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulerbotcommandshandler.SchedulerBotCommandsHandler;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SchedulerBotService {
    private final UserManager userManager;
    private final SchedulerBotCommandsHandler schedulerBotCommandsHandler;

    @Autowired
    public SchedulerBotService(UserManager userManager, SchedulerBotCommandsHandler schedulerBotCommandsHandler) {
        this.userManager = userManager;
        this.schedulerBotCommandsHandler = schedulerBotCommandsHandler;
    }

    @Async
    public void handleRequest(UserRequest userRequest, MessageSender messageSender) {
        try {
            findUserAndScheduleForHisGroup(userRequest, messageSender);
        } catch (NoGroupForUserException e) {
            userManager.handleUserAbsense(userRequest, messageSender);
        }
    }

    private void findUserAndScheduleForHisGroup(UserRequest userRequest, MessageSender messageSender) {
        int id = getUserGroupId(userRequest);
        ScheduleRequest scheduleRequest = new ScheduleRequest(id, userRequest.getMessage());
        schedulerBotCommandsHandler.handleRequest(scheduleRequest, messageSender);
    }

    private int getUserGroupId(UserRequest userRequest) {
        return userManager.getGroupIdOfUser(userRequest.getUserId()).orElseThrow(NoGroupForUserException::new);
    }

}

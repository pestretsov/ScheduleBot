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
    private final UserGroupManager userGroupManager;
    private final SchedulerBotCommandsHandler schedulerBotCommandsHandler;

    @Autowired
    public SchedulerBotService(UserGroupManager userGroupManager, SchedulerBotCommandsHandler schedulerBotCommandsHandler) {
        this.userGroupManager = userGroupManager;
        this.schedulerBotCommandsHandler = schedulerBotCommandsHandler;
    }

    @Async
    public void handleRequest(UserRequest userRequest, MessageWithRepliesSender messageSender) {
        try {
            findUserAndScheduleForHisGroup(userRequest, messageSender);
        } catch (NoGroupForUserException e) {
            userGroupManager.handleUserAbsense(userRequest, messageSender);
        }
    }

    private void findUserAndScheduleForHisGroup(UserRequest userRequest, MessageWithRepliesSender messageSender) {
        int id = getUserGroupId(userRequest);
        ScheduleRequest scheduleRequest = new ScheduleRequest(id, userRequest.getMessage());
        schedulerBotCommandsHandler.handleRequest(scheduleRequest, messageSender);
    }

    private int getUserGroupId(UserRequest userRequest) {
        return userGroupManager.getGroupIdOfUser(userRequest.getUserId()).orElseThrow(NoGroupForUserException::new);
    }

}

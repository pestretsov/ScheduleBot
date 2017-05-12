package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCreationService {
    private final UserGroupManager userGroupManager;

    @Autowired
    public UserCreationService(UserGroupManager userGroupManager) {
        this.userGroupManager = userGroupManager;
    }

    void createUserAndAskForGroup(UserRequest userRequest, MessageSender messageSender) {
        createUser(userRequest);
        askForGroup(messageSender);
    }

    private void askForGroup(MessageSender messageSender) {
        messageSender.sendMessage("Send me your group number like '12345/6' to get your schedule.");
    }

    private void createUser(UserRequest userRequest) {
        userGroupManager.saveUserWithoutGroup(userRequest.getUserId());
    }
}

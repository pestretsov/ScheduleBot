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

    void createNewUser(UserRequest userRequest) {
        userGroupManager.saveUserWithoutGroup(userRequest.getUserId());
    }

}

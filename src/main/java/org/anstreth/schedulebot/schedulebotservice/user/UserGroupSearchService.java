package org.anstreth.schedulebot.schedulebotservice.user;

import org.anstreth.ruzapi.response.Group;
import org.anstreth.schedulebot.exceptions.NoSuchGroupFoundException;
import org.anstreth.schedulebot.response.BotResponse;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulebotservice.user.UserGroupManager;
import org.anstreth.schedulebot.schedulebotservice.user.UserStateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserGroupSearchService {

    private final UserGroupManager userGroupManager;
    private final UserStateManager userStateManager;

    @Autowired
    public UserGroupSearchService(UserGroupManager userGroupManager, UserStateManager userStateManager) {
        this.userGroupManager = userGroupManager;
        this.userStateManager = userStateManager;
    }

    public BotResponse tryToFindUserGroup(UserRequest userRequest, List<String> successReplies) {
        try {
            Group userGroup = findUserGroup(userRequest);
            userStateManager.transitToWithGroup(userRequest.getUserId());
            return getResponseAboutProperGroupSetting(userGroup, successReplies);
        } catch (NoSuchGroupFoundException e) {
            return sendMessageAboutIncorrectGroupName(userRequest);
        }
    }


    private Group findUserGroup(UserRequest userRequest) {
        return userGroupManager.findAndSetGroupForUser(userRequest.getUserId(), userRequest.getMessage());
    }

    private BotResponse getResponseAboutProperGroupSetting(Group group, List<String> replies) {
        String message = String.format("Your group is set to '%s'.", group.getName());
        return new BotResponse(message, replies);
    }

    private BotResponse sendMessageAboutIncorrectGroupName(UserRequest userRequest) {
        String message = String.format("No group by name '%s' is found! Try again.", userRequest.getMessage());
        return new BotResponse(message);
    }


}

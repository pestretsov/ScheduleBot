package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.ruzapi.response.Group;
import org.anstreth.schedulebot.exceptions.NoSuchGroupFoundException;
import org.anstreth.schedulebot.response.BotResponse;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserGroupSearchService {

    private final UserGroupManager userGroupManager;

    @Autowired
    public UserGroupSearchService(UserGroupManager userGroupManager) {
        this.userGroupManager = userGroupManager;
    }

    BotResponse tryToFindUserGroup(UserRequest userRequest, List<String> successReplies) {
        try {
            Group userGroup = findUserGroup(userRequest);
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

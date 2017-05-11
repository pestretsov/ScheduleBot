package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.ruzapi.response.Group;
import org.anstreth.schedulebot.exceptions.NoSuchGroupFoundException;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserGroupSearchService {

    private UserGroupManager userGroupManager;

    @Autowired
    public UserGroupSearchService(UserGroupManager userGroupManager) {
        this.userGroupManager = userGroupManager;
    }

    void tryToFindUserGroup(UserRequest userRequest, MessageWithRepliesSender messageSender, List<String> successReplies) {
        try {
            Group userGroup = findUserGroup(userRequest);
            sendMessageAboutProperGroupSetting(messageSender.withReplies(successReplies), userGroup);
        } catch (NoSuchGroupFoundException e) {
            sendMessageAboutIncorrectGroupName(messageSender, userRequest);
        }
    }

    private Group findUserGroup(UserRequest userRequest) {
        return userGroupManager.findAndSetGroupForUser(userRequest.getUserId(), userRequest.getMessage());
    }

    private void sendMessageAboutProperGroupSetting(MessageSender messageSender, Group group) {
        String message = String.format("Your group is set to '%s'.", group.getName());
        messageSender.sendMessage(message);
    }

    private void sendMessageAboutIncorrectGroupName(MessageSender messageSender, UserRequest userRequest) {
        String message = String.format("No group by name '%s' is found! Try again.", userRequest.getMessage());
        messageSender.sendMessage(message);
    }

}

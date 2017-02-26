package org.anstreth.schedulebot.scheduleuserservice;

import org.anstreth.ruzapi.response.Group;
import org.anstreth.ruzapi.response.Groups;
import org.anstreth.ruzapi.ruzapirepository.GroupsRepository;
import org.anstreth.schedulebot.model.User;
import org.anstreth.schedulebot.schedulerbotcommandshandler.SchedulerBotCommandsHandler;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerrepository.UserRepository;
import org.anstreth.schedulebot.scheduleuserservice.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SchedulerUserService {
    private static final String NO_GROUP_SPECIFIED_MESSAGE = "Send me your group number like '12345/6' to get your schedule.";
    private final SchedulerBotCommandsHandler schedulerBotCommandsHandler;
    private final UserRepository userRepository;
    private final GroupsRepository groupsRepository;

    @Autowired
    public SchedulerUserService(UserRepository userRepository, GroupsRepository groupsRepository, SchedulerBotCommandsHandler schedulerBotCommandsHandler) {
        this.schedulerBotCommandsHandler = schedulerBotCommandsHandler;
        this.userRepository = userRepository;
        this.groupsRepository = groupsRepository;
    }

    public void handleRequest(UserRequest userRequest, MessageSender messageSender) {
        User user = userRepository.getUserById(userRequest.getUserId());
        if (user == null) {
            createUserWithoutGroup(userRequest.getUserId());
            messageSender.sendMessage(NO_GROUP_SPECIFIED_MESSAGE);
        } else if (userGroupIsNotSpecified(user)) {
            Group userGroup = findUserGroup(userRequest).get();
            updateUsersGroup(user, userGroup);
            messageSender.sendMessage(getGroupSettingMessage(userGroup));
        } else {
            schedulerBotCommandsHandler.handleRequest(new ScheduleRequest(user.getGroupId(), userRequest.getMessage()), messageSender);
        }
    }

    private void createUserWithoutGroup(long userId) {
        userRepository.save(new User(userId, User.NO_GROUP_SPECIFIED));
    }

    private boolean userGroupIsNotSpecified(User user) {
        return user.getGroupId() == User.NO_GROUP_SPECIFIED;
    }

    private Optional<Group> findUserGroup(UserRequest userRequest) {
        Groups groups = groupsRepository.findGroupsByName(userRequest.getMessage());
        return Optional.ofNullable(groups.getGroups()).map(groupsList -> groupsList.get(0));
    }

    private User updateUsersGroup(User user, Group userGroup) {
        return userRepository.save(new User(user.getId(), userGroup.getId()));
    }

    private String getGroupSettingMessage(Group userGroup) {
        return String.format("Your group is set to '%s'.", userGroup.getName());
    }
}

package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.ruzapi.response.Group;
import org.anstreth.ruzapi.response.Groups;
import org.anstreth.ruzapi.ruzapirepository.GroupsRepository;
import org.anstreth.schedulebot.model.User;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulerrepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class UserManager {
    private final UserRepository userRepository;
    private final GroupsRepository groupsRepository;

    @Autowired
    UserManager(UserRepository userRepository, GroupsRepository groupsRepository) {
        this.userRepository = userRepository;
        this.groupsRepository = groupsRepository;
    }

    Optional<Integer> getGroupIdOfUser(long userId) {
        return Optional.ofNullable(userRepository.getUserById(userId))
                .filter(this::userGroupIsSpecified)
                .map(User::getGroupId);
    }

    void handleUserAbsense(UserRequest userRequest, MessageSender messageSender) {
        User user = userRepository.getUserById(userRequest.getUserId());

        if (user == null) {
            saveUserWithoutGroup(userRequest.getUserId());
            askUserForGroup(messageSender);
            return;
        }

        if (!userGroupIsSpecified(user)) {
            Optional<Group> group = findUserGroupByGroupName(userRequest);
            if (group.isPresent()) {
                updateUserWithGroup(user, group.get());
                sendMessageAboutProperGroupSetting(messageSender, group.get());
            } else {
                sendMessageAboutIncorrectGroupName(messageSender, userRequest);
            }
        }
    }

    private void sendMessageAboutIncorrectGroupName(MessageSender messageSender, UserRequest userRequest) {
        String message = String.format("No group by name '%s' is found! Try again.", userRequest.getMessage());
        messageSender.sendMessage(message);
    }

    private User saveUserWithoutGroup(long userId) {
        return userRepository.save(new User(userId, User.NO_GROUP_SPECIFIED));
    }

    private void askUserForGroup(MessageSender messageSender) {
        messageSender.sendMessage("Send me your group number like '12345/6' to get your schedule.");
    }

    private Optional<Group> findUserGroupByGroupName(UserRequest userRequest) {
        Groups groups = groupsRepository.findGroupsByName(userRequest.getMessage());
        if (groups.getGroups() != null && !groups.getGroups().isEmpty()) {
            return Optional.of(groups.getGroups().get(0));
        }

        return Optional.empty();
    }

    private User updateUserWithGroup(User user, Group group) {
        return userRepository.save(new User(user.getId(), group.getId()));
    }

    private void sendMessageAboutProperGroupSetting(MessageSender messageSender, Group group) {
        String message = String.format("Your group is set to '%s'.", group.getName());
        messageSender.sendMessage(message);
    }

    private boolean userGroupIsSpecified(User user) {
        return user.getGroupId() != User.NO_GROUP_SPECIFIED;
    }
}

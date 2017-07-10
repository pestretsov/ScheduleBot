package org.anstreth.schedulebot.schedulebotservice.user;

import org.anstreth.ruzapi.response.Group;
import org.anstreth.ruzapi.response.Groups;
import org.anstreth.ruzapi.ruzapirepository.GroupsRepository;
import org.anstreth.schedulebot.exceptions.NoGroupForUserException;
import org.anstreth.schedulebot.exceptions.NoSuchGroupFoundException;
import org.anstreth.schedulebot.exceptions.NoSuchUserException;
import org.anstreth.schedulebot.model.User;
import org.anstreth.schedulebot.schedulerrepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserGroupManager {
    private final UserRepository userRepository;
    private final GroupsRepository groupsRepository;

    @Autowired
    UserGroupManager(UserRepository userRepository, GroupsRepository groupsRepository) {
        this.userRepository = userRepository;
        this.groupsRepository = groupsRepository;
    }

    public int getGroupIdOfUser(long userId) {
        User user = getUser(userId)
                .orElseThrow(() -> new NoSuchUserException(userId));
        assertGroupIsSpecified(user);
        return user.getGroupId();
    }

    public User saveUserWithoutGroup(long userId) {
        return userRepository.save(new User(userId, User.NO_GROUP_SPECIFIED));
    }

    public Group findAndSetGroupForUser(long userId, String groupName) {
        Group group = findGroupByName(groupName);
        User user = userRepository.getUserById(userId);
        updateUserWithGroup(user, group);
        return group;
    }

    private void assertGroupIsSpecified(User user) {
        if (!userGroupIsSpecified(user))
            throw new NoGroupForUserException(user.getId());
    }

    private Optional<User> getUser(long userId) {
        return Optional.ofNullable(userRepository.getUserById(userId));
    }

    private boolean userGroupIsSpecified(User user) {
        return user.getGroupId() != User.NO_GROUP_SPECIFIED;
    }

    private Group findGroupByName(String groupName) {
        Groups groups = groupsRepository.findGroupsByName(groupName);
        if (groups.getGroups() == null || groups.getGroups().isEmpty())
            throw new NoSuchGroupFoundException(groupName);

        return groups.getGroups().get(0);
    }

    private void updateUserWithGroup(User user, Group group) {
        userRepository.save(new User(user.getId(), group.getId()));
    }
}

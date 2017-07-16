package org.anstreth.schedulebot.schedulebotservice.user;

import org.anstreth.schedulebot.model.User;
import org.anstreth.schedulebot.model.UserState;
import org.anstreth.schedulebot.schedulerrepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserStateManager {

    private final UserRepository userRepository;

    @Autowired
    public UserStateManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User transitToAskedForGroup(User user) {
        return updateUserState(user, UserState.ASKED_FOR_GROUP);
    }

    public User transitToAskedForGroup(long userId) {
        return transitToAskedForGroup(userRepository.getUserById(userId));
    }

    public User transitToWithGroup(User user) {
        return updateUserState(user, UserState.WITH_GROUP);
    }

    public User transitToWithGroup(long userId) {
        return transitToWithGroup(userRepository.getUserById(userId));
    }

    public User transitToMenu(long userId) {
        return updateUserState(userRepository.getUserById(userId), UserState.MENU);
    }

    private User updateUserState(User user, UserState state) {
        return userRepository.save(new User(user.getId(), user.getGroupId(), state));
    }
}
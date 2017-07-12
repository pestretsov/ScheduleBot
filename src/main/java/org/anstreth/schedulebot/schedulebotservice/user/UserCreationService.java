package org.anstreth.schedulebot.schedulebotservice.user;

import org.anstreth.schedulebot.model.User;
import org.anstreth.schedulebot.model.UserState;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulerrepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCreationService {
    private final UserRepository userRepository;

    @Autowired
    public UserCreationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createNewUser(UserRequest userRequest) {
        return userRepository.save(userWithNoGroup(userRequest.getUserId()));
    }

    private User userWithNoGroup(long userId) {
        return new User(userId, User.NO_GROUP_SPECIFIED, UserState.NO_GROUP);
    }

}

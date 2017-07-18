package org.anstreth.schedulebot.schedulebotservice.user;

import org.anstreth.schedulebot.model.User;
import org.anstreth.schedulebot.model.UserState;
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

    public User createNewUser(long userId) {
        return userRepository.save(userWithNoGroup(userId));
    }

    private User userWithNoGroup(long userId) {
        return new User(userId, UserState.NO_GROUP);
    }

}

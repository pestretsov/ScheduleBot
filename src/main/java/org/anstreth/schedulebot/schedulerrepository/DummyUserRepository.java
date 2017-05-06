package org.anstreth.schedulebot.schedulerrepository;

import org.anstreth.schedulebot.model.User;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class DummyUserRepository implements UserRepository {

    private final Map<Long, User> users = new ConcurrentHashMap<>();

    @Override
    public User getUserById(long id) {
        return users.get(id);
    }

    @Override
    public User save(User user) {
        users.put(user.getId(), user);
        return users.get(user.getId());
    }

    @Override
    public User delete(User user) {
        return users.remove(user.getId());
    }
}

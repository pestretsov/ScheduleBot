package org.anstreth.schedulebot.schedulerrepository;

import org.anstreth.schedulebot.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    User getUserById(long id);
    User save(User user);
    User delete(User user);
}

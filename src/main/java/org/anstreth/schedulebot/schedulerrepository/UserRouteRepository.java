package org.anstreth.schedulebot.schedulerrepository;

import org.anstreth.schedulebot.model.UserRoute;

public interface UserRouteRepository {
    UserRoute get(long userId);
    void save(long userId, UserRoute userState);
    void remove(long userId);
}

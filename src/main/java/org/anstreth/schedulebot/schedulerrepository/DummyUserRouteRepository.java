package org.anstreth.schedulebot.schedulerrepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.anstreth.schedulebot.model.UserRoute;

public class DummyUserRouteRepository implements UserRouteRepository {
    private final Map<Long, UserRoute> routes = new ConcurrentHashMap<>();
    private static final String NULL_ROUTE_MESSAGE = "UserRoute cannot be null; use remove to delete route!";

    @Override
    public UserRoute get(long userId) {
        return routes.get(userId);
    }

    @Override
    public void save(long userId, UserRoute userState) {
        assertUserState(userState);
        routes.put(userId, userState);
    }

    @Override
    public void remove(long userId) {
        routes.remove(userId);
    }

    private void assertUserState(UserRoute userState) {
        if (userState == null) {
            throw new IllegalArgumentException(NULL_ROUTE_MESSAGE);
        }
    }
}

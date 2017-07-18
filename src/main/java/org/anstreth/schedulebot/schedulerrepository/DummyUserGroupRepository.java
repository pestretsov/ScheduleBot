package org.anstreth.schedulebot.schedulerrepository;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public final class DummyUserGroupRepository implements UserGroupRepository {
    private final Map<Long, Integer> groupIdsByUser = new ConcurrentHashMap<>();

    @Override
    public Integer get(long userId) {
        return groupIdsByUser.get(userId);
    }

    @Override
    public void save(long userId, int groupId) {
        groupIdsByUser.put(userId, groupId);
    }

    @Override
    public void remove(long userId) {
        groupIdsByUser.remove(userId);
    }
}

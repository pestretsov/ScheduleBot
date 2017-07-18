package org.anstreth.schedulebot.schedulerrepository;

public interface UserGroupRepository {
    Integer get(long userId);
    void save(long userId, int groupId);
    void remove(long userId);
}

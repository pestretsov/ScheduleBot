package org.anstreth.schedulebot.model;

import lombok.Data;

@Data
public class User {
    public final static int NO_GROUP_SPECIFIED = -1;

    final long id;
    final int groupId;
    public final UserState state;

    public User(long id, int groupId, UserState state) {
        this.id = id;
        this.groupId = groupId;
        this.state = state;
    }

    public User(long id, int groupId) {
        this(id, groupId, UserState.NO_GROUP);
    }
}

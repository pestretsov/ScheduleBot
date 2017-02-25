package org.anstreth.schedulebot.model;

import lombok.Data;

@Data
public class User {
    public final static int NO_GROUP_SPECIFIED = -1;

    final long id;
    final int groupId;
}

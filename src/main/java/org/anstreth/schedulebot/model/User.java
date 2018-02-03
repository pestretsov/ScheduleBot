package org.anstreth.schedulebot.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private final long id;
    private final UserState state;

    public User(long id) {
        this(id, UserState.NO_GROUP);
    }
}

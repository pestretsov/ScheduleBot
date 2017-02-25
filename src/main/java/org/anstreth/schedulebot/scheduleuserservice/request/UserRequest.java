package org.anstreth.schedulebot.scheduleuserservice.request;

import lombok.Data;

@Data
public class UserRequest {
    private final long userId;
    private final String message;
}

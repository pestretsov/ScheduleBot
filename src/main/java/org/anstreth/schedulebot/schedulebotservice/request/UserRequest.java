package org.anstreth.schedulebot.schedulebotservice.request;

import lombok.Data;

@Data
public class UserRequest {
    private final long userId;
    private final String message;
    private final boolean groupChat;
}

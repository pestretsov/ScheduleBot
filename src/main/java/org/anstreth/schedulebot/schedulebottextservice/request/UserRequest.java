package org.anstreth.schedulebot.schedulebottextservice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserRequest {
    private long userId;
    private String message;
}

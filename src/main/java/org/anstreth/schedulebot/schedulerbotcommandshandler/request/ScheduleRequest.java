package org.anstreth.schedulebot.schedulerbotcommandshandler.request;

import lombok.Data;

@Data
public class ScheduleRequest {
    private final int groupId;
    private final String message;
}

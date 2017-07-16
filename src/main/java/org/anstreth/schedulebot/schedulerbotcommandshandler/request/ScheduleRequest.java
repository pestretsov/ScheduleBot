package org.anstreth.schedulebot.schedulerbotcommandshandler.request;

import lombok.Data;
import org.anstreth.schedulebot.commands.UserCommand;

@Data
public class ScheduleRequest {
    private final int groupId;
    private final UserCommand command;
}

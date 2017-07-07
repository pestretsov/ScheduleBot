package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;

import java.util.List;

public interface ScheduleResponse {
    List<String> formatWith(SchedulerFormatter schedulerFormatter);
}

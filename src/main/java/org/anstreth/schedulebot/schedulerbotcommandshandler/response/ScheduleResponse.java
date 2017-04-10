package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import org.anstreth.schedulebot.schedulebotservice.MessageSender;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;

public interface ScheduleResponse {
    void formatAndSend(SchedulerFormatter schedulerFormatter, MessageSender messageSender);
}

package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import org.anstreth.schedulebot.schedulebotservice.MessageSender;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;

import java.util.List;

public interface ScheduleResponse {
    void formatAndSend(SchedulerFormatter schedulerFormatter, MessageSender messageSender);
    List<String> format(SchedulerFormatter schedulerFormatter);
}

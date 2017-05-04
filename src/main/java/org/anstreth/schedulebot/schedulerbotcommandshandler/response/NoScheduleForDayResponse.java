package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import org.anstreth.schedulebot.schedulebotservice.MessageSender;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;

import java.util.Calendar;

public class NoScheduleForDayResponse implements ScheduleResponse {
    private final Calendar date;

    public NoScheduleForDayResponse(Calendar date) {
        this.date = date;
    }

    @Override
    public void formatAndSend(SchedulerFormatter schedulerFormatter, MessageSender messageSender) {
        messageSender.sendMessage(schedulerFormatter.getNoScheduleForDateMessage(date));
    }
}

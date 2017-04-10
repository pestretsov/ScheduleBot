package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import org.anstreth.ruzapi.response.Day;
import org.anstreth.schedulebot.schedulebotservice.MessageSender;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;

public class DayResponse implements ScheduleResponse {
    private Day day;

    DayResponse(Day day) {
        this.day = day;
    }

    @Override
    public void formatAndSend(SchedulerFormatter schedulerFormatter, MessageSender messageSender) {
        messageSender.sendMessage(schedulerFormatter.formatDay(day));
    }
}

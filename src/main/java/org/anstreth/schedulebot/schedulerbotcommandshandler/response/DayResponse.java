package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import org.anstreth.ruzapi.response.Day;
import org.anstreth.schedulebot.schedulebotservice.MessageSender;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;

import java.util.Collections;
import java.util.List;

public class DayResponse implements ScheduleResponse {
    private final Day day;

    public DayResponse(Day day) {
        this.day = day;
    }

    @Override
    public void formatAndSend(SchedulerFormatter schedulerFormatter, MessageSender messageSender) {
        messageSender.sendMessage(schedulerFormatter.formatDay(day));
    }

    @Override
    public List<String> format(SchedulerFormatter formatter) {
        return Collections.singletonList(formatter.formatDay(day));
    }
}

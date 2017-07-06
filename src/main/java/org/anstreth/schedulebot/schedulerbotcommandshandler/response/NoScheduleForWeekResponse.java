package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class NoScheduleForWeekResponse implements ScheduleResponse {
    private final Calendar date;

    public NoScheduleForWeekResponse(Calendar date) {
        this.date = date;
    }

    @Override
    public List<String> format(SchedulerFormatter formatter) {
        return Collections.singletonList(formatter.getNoScheduleForWeekMessage(date));
    }
}

package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import java.util.Calendar;
import java.util.List;
import lombok.Getter;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;

public class NoScheduleForWeekResponse implements ScheduleResponse {

    @Getter
    private final Calendar date;

    public NoScheduleForWeekResponse(Calendar date) {
        this.date = date;
    }

    @Override
    public List<String> formatWith(SchedulerFormatter formatter) {
        return formatter.format(this);
    }
}

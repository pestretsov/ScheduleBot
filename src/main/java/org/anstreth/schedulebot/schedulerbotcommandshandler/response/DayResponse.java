package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import java.util.List;
import lombok.Getter;
import org.anstreth.ruzapi.response.Day;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;

public class DayResponse implements ScheduleResponse {

    @Getter
    private final Day day;

    public DayResponse(Day day) {
        this.day = day;
    }

    @Override
    public List<String> formatWith(SchedulerFormatter formatter) {
        return formatter.format(this);
    }
}

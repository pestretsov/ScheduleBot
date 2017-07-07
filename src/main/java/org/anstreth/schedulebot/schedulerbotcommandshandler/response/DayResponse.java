package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import lombok.Getter;
import org.anstreth.ruzapi.response.Day;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;

import java.util.Collections;
import java.util.List;

public class DayResponse implements ScheduleResponse {

    @Getter
    private final Day day;

    public DayResponse(Day day) {
        this.day = day;
    }

    @Override
    public List<String> format(SchedulerFormatter formatter) {
        return Collections.singletonList(formatter.formatDay(day));
    }
}

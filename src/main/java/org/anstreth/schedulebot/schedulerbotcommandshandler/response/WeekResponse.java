package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import java.util.List;
import lombok.Getter;
import org.anstreth.ruzapi.response.WeekSchedule;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;

public class WeekResponse implements ScheduleResponse {

    @Getter
    private final WeekSchedule weekSchedule;

    public WeekResponse(WeekSchedule weekSchedule) {
        this.weekSchedule = weekSchedule;
    }

    @Override
    public List<String> formatWith(SchedulerFormatter formatter) {
        return formatter.format(this);
    }
}

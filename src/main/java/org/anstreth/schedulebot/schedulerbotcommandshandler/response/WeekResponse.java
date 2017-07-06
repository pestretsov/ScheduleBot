package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import lombok.Getter;
import org.anstreth.ruzapi.response.WeekSchedule;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class WeekResponse implements ScheduleResponse {

    private final WeekSchedule weekSchedule;

    public WeekResponse(WeekSchedule weekSchedule) {
        this.weekSchedule = weekSchedule;
    }

    @Override
    public List<String> format(SchedulerFormatter schedulerFormatter) {
        return weekSchedule.getDays().stream()
                .map(schedulerFormatter::formatDay)
                .collect(Collectors.toList());
    }
}

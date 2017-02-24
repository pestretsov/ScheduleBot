package org.anstreth.schedulebot.schedulerformatter;

import org.anstreth.ruzapi.response.Day;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public interface SchedulerFormatter {
    String formatDay(Day scheduleForToday);
    String getNoScheduleForDateMessage(Calendar calendar);
}

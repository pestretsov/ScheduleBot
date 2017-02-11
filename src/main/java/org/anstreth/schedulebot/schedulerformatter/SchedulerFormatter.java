package org.anstreth.schedulebot.schedulerformatter;

import org.anstreth.ruzapi.Day;
import org.springframework.stereotype.Component;

@Component
public interface SchedulerFormatter {
    String formatDay(Day scheduleForToday);
}

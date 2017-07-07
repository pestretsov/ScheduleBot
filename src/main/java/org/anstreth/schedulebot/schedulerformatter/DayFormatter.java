package org.anstreth.schedulebot.schedulerformatter;

import org.anstreth.ruzapi.response.Day;

public interface DayFormatter {
    String formatDay(Day scheduleForToday);
}

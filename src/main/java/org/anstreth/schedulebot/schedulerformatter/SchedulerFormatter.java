package org.anstreth.schedulebot.schedulerformatter;

import org.anstreth.ruzapi.response.Day;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.*;

import java.util.Collections;
import java.util.List;

public interface SchedulerFormatter {
    @Deprecated
    String formatDay(Day scheduleForToday);

    List<String> format(NoScheduleForDayResponse response);

    List<String> format(NoScheduleForWeekResponse response);

    List<String> format(DayResponse dayResponse);

    List<String> format(WeekResponse weekResponse);

    default List<String> format(SimpleStringResponse response) {
        return Collections.singletonList(response.getMessage());
    }
}

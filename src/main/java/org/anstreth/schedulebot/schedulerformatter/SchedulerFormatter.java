package org.anstreth.schedulebot.schedulerformatter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.anstreth.ruzapi.response.Day;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.DayResponse;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.NoScheduleForDayResponse;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.NoScheduleForWeekResponse;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.SimpleStringResponse;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.WeekResponse;
import org.springframework.stereotype.Component;

@Component
public interface SchedulerFormatter {
    @Deprecated
    String formatDay(Day scheduleForToday);

    List<String> format(NoScheduleForDayResponse response);

    List<String> format(NoScheduleForWeekResponse response);

    default List<String> format(DayResponse dayResponse) {
        return Collections.singletonList(formatDay(dayResponse.getDay()));
    }

    default List<String> format(WeekResponse weekResponse) {
        return weekResponse.getWeekSchedule().getDays().stream()
            .map(this::formatDay)
            .collect(Collectors.toList());
    }

    default List<String> format(SimpleStringResponse response) {
        return Collections.singletonList(response.getMessage());
    }
}

package org.anstreth.schedulebot.schedulerformatter;

import org.anstreth.schedulebot.schedulerbotcommandshandler.response.*;

import java.util.List;

public interface SchedulerFormatter {

    List<String> format(NoScheduleForDayResponse response);

    List<String> format(NoScheduleForWeekResponse response);

    List<String> format(DayResponse dayResponse);

    List<String> format(WeekResponse weekResponse);

    List<String> format(SimpleStringResponse response);
}

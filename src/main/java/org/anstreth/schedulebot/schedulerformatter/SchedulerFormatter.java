package org.anstreth.schedulebot.schedulerformatter;

import org.anstreth.schedulebot.schedulerbotcommandshandler.response.*;

import java.util.List;

/**
 * This interface is supposed to be used as a visitor.
 *
 * All of its methods return Lists because some responses may have many messages inside.
 * Particular one is a WeekResponse.
 *
 * @author Roman Golyshev
 */
public interface SchedulerFormatter {
    List<String> format(DayResponse dayResponse);
    List<String> format(WeekResponse weekResponse);
    List<String> format(SimpleStringResponse response);
    List<String> format(NoScheduleForDayResponse response);
    List<String> format(NoScheduleForWeekResponse response);
}

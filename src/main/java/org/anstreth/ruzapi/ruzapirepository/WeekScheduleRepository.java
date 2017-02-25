package org.anstreth.ruzapi.ruzapirepository;

import org.anstreth.ruzapi.response.WeekSchedule;

import java.util.Calendar;

public interface WeekScheduleRepository {
    WeekSchedule getWeekScheduleForGroupForDate(int groupId, Calendar date);
}

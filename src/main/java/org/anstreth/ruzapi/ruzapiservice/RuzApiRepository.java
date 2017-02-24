package org.anstreth.ruzapi.ruzapiservice;

import org.anstreth.ruzapi.response.WeekSchedule;

import java.util.Calendar;

public interface RuzApiRepository {
    WeekSchedule getWeekScheduleForDate(Calendar date);
}

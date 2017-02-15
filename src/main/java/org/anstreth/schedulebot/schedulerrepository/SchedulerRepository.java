package org.anstreth.schedulebot.schedulerrepository;

import org.anstreth.ruzapi.Day;
import org.anstreth.ruzapi.WeekSchedule;
import org.springframework.stereotype.Repository;

import java.util.Calendar;

@Repository
public interface SchedulerRepository {
    Day getScheduleForDay(Calendar date);
    WeekSchedule getScheduleForWeek(Calendar date);
}

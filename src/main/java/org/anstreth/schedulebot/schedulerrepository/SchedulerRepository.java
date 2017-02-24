package org.anstreth.schedulebot.schedulerrepository;

import org.anstreth.ruzapi.response.Day;
import org.anstreth.ruzapi.response.WeekSchedule;
import org.anstreth.schedulebot.exceptions.NoScheduleForDay;
import org.springframework.stereotype.Repository;

import java.util.Calendar;

@Repository
public interface SchedulerRepository {
    Day getScheduleForDay(Calendar date) throws NoScheduleForDay;
    WeekSchedule getScheduleForWeek(Calendar date);
}

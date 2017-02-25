package org.anstreth.schedulebot.schedulerrepository;

import org.anstreth.ruzapi.response.Day;
import org.anstreth.ruzapi.response.WeekSchedule;
import org.anstreth.schedulebot.exceptions.NoScheduleForDay;
import org.springframework.stereotype.Repository;

import java.util.Calendar;

@Repository
public interface SchedulerRepository {
    Day getScheduleForGroupForDay(int groupId, Calendar date) throws NoScheduleForDay;
    WeekSchedule getScheduleForGroupForWeek(int groupId, Calendar date);
}

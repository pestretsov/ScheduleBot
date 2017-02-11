package org.anstreth.schedulebot.schedulerrepository;

import org.anstreth.ruzapi.Day;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulerRepository {
    Day getScheduleForToday();
}

package org.anstreth.schedulebot.schedulerrepository;

import org.anstreth.ruzapi.response.Day;
import org.anstreth.ruzapi.response.WeekSchedule;
import org.anstreth.ruzapi.ruzapiservice.RuzApiRepository;
import org.anstreth.schedulebot.exceptions.NoScheduleForDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Calendar;

@Repository
class RuzApiSchedulerRepository implements SchedulerRepository {

    private RuzApiRepository ruzApiRepository;

    @Autowired
    public RuzApiSchedulerRepository(RuzApiRepository ruzApiRepository) {
        this.ruzApiRepository = ruzApiRepository;
    }

    @Override
    public Day getScheduleForDay(Calendar date) throws NoScheduleForDay {
        int weekDay = getWeekDay(date);
        WeekSchedule weekSchedule = getScheduleForWeek(date);
        return getDayOfWeekFromSchedule(weekSchedule, weekDay);
    }

    private Day getDayOfWeekFromSchedule(WeekSchedule weekSchedule, int weekDay) {
        return weekSchedule.getDays().stream()
                .filter(day -> day.getWeekDay() == weekDay)
                .findFirst().orElseThrow(NoScheduleForDay::new);
    }

    private int getWeekDay(Calendar date) {
        return date.get(Calendar.DAY_OF_WEEK) - 1;
    }

    @Override
    public WeekSchedule getScheduleForWeek(Calendar date) {
        return ruzApiRepository.getWeekScheduleForDate(date);
    }

}

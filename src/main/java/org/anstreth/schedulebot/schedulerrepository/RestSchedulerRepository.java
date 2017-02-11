package org.anstreth.schedulebot.schedulerrepository;

import org.anstreth.ruzapi.Day;
import org.anstreth.ruzapi.WeekSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.NoSuchElementException;

@Repository
class RestSchedulerRepository implements SchedulerRepository {

    @Value("${ruzapi.myschedule}")
    private String myGroupScheduleURL;
    private final RestTemplate restTemplate;

    @Autowired
    public RestSchedulerRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Day getScheduleForToday() throws NoSuchElementException{
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        throwNoSuchElementExceptionIfTodayIsSunday(calendar);

        WeekSchedule weekSchedule = restTemplate.getForObject(myGroupScheduleURL, WeekSchedule.class);
        return weekSchedule.getDays().get(day - 2);
    }

    private void throwNoSuchElementExceptionIfTodayIsSunday(Calendar calendar) {
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day == Calendar.SUNDAY) {
            throw new NoSuchElementException("");
        }
    }
}

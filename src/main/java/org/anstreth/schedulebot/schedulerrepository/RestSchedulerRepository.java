package org.anstreth.schedulebot.schedulerrepository;

import org.anstreth.ruzapi.Day;
import org.anstreth.ruzapi.WeekSchedule;
import org.anstreth.schedulebot.exceptions.NoScheduleForSundayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.NoSuchElementException;

@Repository
class RestSchedulerRepository implements SchedulerRepository {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Value("${ruzapi.myschedule}")
    private String myGroupScheduleURL;
    private final RestTemplate restTemplate;

    @Autowired
    public RestSchedulerRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Day getScheduleForDay(Calendar calendar) throws NoSuchElementException {
        throwExceptionIfTodayIsSundaySunday(calendar);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        WeekSchedule weekSchedule = makeApiCall(calendar);
        return weekSchedule.getDays().get(day - 2);
    }

    private WeekSchedule makeApiCall(Calendar date) {
        return restTemplate.getForObject(
                myGroupScheduleURL,
                WeekSchedule.class,
                Collections.singletonMap("date", simpleDateFormat.format(date.getTime())));
    }

    private void throwExceptionIfTodayIsSundaySunday(Calendar calendar) {
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day == Calendar.SUNDAY) {
            throw new NoScheduleForSundayException();
        }
    }
}

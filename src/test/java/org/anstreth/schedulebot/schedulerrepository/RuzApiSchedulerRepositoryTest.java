package org.anstreth.schedulebot.schedulerrepository;

import org.anstreth.ruzapi.response.Day;
import org.anstreth.ruzapi.response.WeekSchedule;
import org.anstreth.ruzapi.ruzapiservice.RuzApiRepository;
import org.anstreth.schedulebot.exceptions.NoScheduleForDay;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Calendar;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RuzApiSchedulerRepositoryTest {
    @InjectMocks
    private RuzApiSchedulerRepository ruzApiSchedulerRepository;

    @Mock
    private RuzApiRepository ruzApiRepository;

    @Test(expected = NoScheduleForDay.class)
    public void ifThereAreNoScheduleForPassedDateExceptionIsThrown() throws Exception {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        WeekSchedule weekSchedule = getWeekScheduleWithDays();
        when(ruzApiRepository.getWeekScheduleForDate(date)).thenReturn(weekSchedule);

        ruzApiSchedulerRepository.getScheduleForDay(date);
    }

    @Test
    public void dayIsFoundByItsWeekdayField() throws Exception {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        Day dayWithNumber = getDayWithDayWeek(2);
        WeekSchedule weekSchedule = getWeekScheduleWithDays(dayWithNumber);
        when(ruzApiRepository.getWeekScheduleForDate(date)).thenReturn(weekSchedule);

        Day returnedDay = ruzApiSchedulerRepository.getScheduleForDay(date);

        assertThat(returnedDay, is(dayWithNumber));
    }

    private WeekSchedule getWeekScheduleWithDays(Day ...days) {
        WeekSchedule weekSchedule = new WeekSchedule();
        weekSchedule.setDays(Arrays.asList(days));
        return weekSchedule;
    }

    private Day getDayWithDayWeek(int weekDay) {
        Day dayWithNumber = new Day();
        dayWithNumber.setWeekDay(weekDay);
        return dayWithNumber;
    }
}
package org.anstreth.schedulebot.schedulerformatter;

import org.anstreth.ruzapi.response.Day;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Calendar;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class SimpleSchedulerFormatterTest {
    @InjectMocks
    private SimpleSchedulerFormatter schedulerFormatter;

    @Mock
    private DayFormatter dayFormatter;

    @Test
    public void dayFormattedWith_dayFormatter() throws Exception {
        Day mockDay = mock(Day.class);
        String formattedDay = "formattedDay";
        doReturn(formattedDay).when(dayFormatter).formatDay(mockDay);

        assertThat(schedulerFormatter.formatDay(mockDay), is(formattedDay));
    }

    @Test
    public void getNoScheduleForDayMessageInsertsPassedDateToMessage() throws Exception {
        String message = schedulerFormatter.getNoScheduleForDateMessage(Calendar.getInstance());

        assertThat(message, is("There are no lessons!"));
    }

    @Test
    public void getNoScheduleForWeekReturnsCorrectMessage() {
        String message = schedulerFormatter.getNoScheduleForWeekMessage(Calendar.getInstance());

        assertThat(message, is("There are no schedule for this week!"));
    }

}

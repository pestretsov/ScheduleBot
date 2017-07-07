package org.anstreth.schedulebot.schedulerformatter;

import java.util.List;
import org.anstreth.ruzapi.response.Day;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.NoScheduleForDayResponse;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.NoScheduleForWeekResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
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
    public void NoScheduleForDay_reponseIsFormattedCorrectly() throws Exception {
        List<String> messages = schedulerFormatter.format(mock(NoScheduleForDayResponse.class));

        assertThat(messages, contains("There are no lessons!"));
    }

    @Test
    public void NoScheduleForWeek_reponseIsFormattedCorrectly() {
        List<String> messages = schedulerFormatter.format(mock(NoScheduleForWeekResponse.class));

        assertThat(messages, contains("There are no schedule for this week!"));
    }

}

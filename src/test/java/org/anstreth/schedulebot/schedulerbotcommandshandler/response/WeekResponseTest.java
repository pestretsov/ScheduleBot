package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import org.anstreth.ruzapi.response.Day;
import org.anstreth.ruzapi.response.WeekSchedule;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class WeekResponseTest {

    @Mock
    private SchedulerFormatter formatter;

    @Test
    public void responseShouldFormatEachDayAndReturnThem() throws Exception {
        Day dayOne = mock(Day.class);
        Day dayTwo = mock(Day.class);
        String formattedDayOne = "day one";
        String formattedDayTwo = "day two";
        WeekSchedule weekSchedule = new WeekSchedule();
        weekSchedule.setDays(Arrays.asList(dayOne, dayTwo));
        WeekResponse weekResponse = new WeekResponse(weekSchedule);
        given(formatter.formatDay(dayOne)).willReturn(formattedDayOne);
        given(formatter.formatDay(dayTwo)).willReturn(formattedDayTwo);

        assertThat(weekResponse.format(formatter), contains(formattedDayOne, formattedDayTwo));
    }
}
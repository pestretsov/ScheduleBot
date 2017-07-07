package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import java.util.Arrays;
import org.anstreth.ruzapi.response.WeekSchedule;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class WeekResponseTest {

    @Mock
    private SchedulerFormatter formatter;

    @Test
    public void responseShouldFormatEachDayAndReturnThem() throws Exception {
        String formattedDayOne = "day one";
        String formattedDayTwo = "day two";
        WeekSchedule weekSchedule = new WeekSchedule();
        WeekResponse weekResponse = new WeekResponse(weekSchedule);
        doReturn(Arrays.asList(formattedDayOne, formattedDayTwo)).when(formatter).format(weekResponse);

        assertThat(weekResponse.formatWith(formatter), contains(formattedDayOne, formattedDayTwo));
    }
}

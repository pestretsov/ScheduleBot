package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import java.util.Calendar;
import java.util.Collections;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class NoScheduleForDayResponseTest {

    @Mock
    private SchedulerFormatter formatter;

    @Test
    public void responseUses_formatter_toFormatItself() {
        Calendar date = Calendar.getInstance();
        NoScheduleForDayResponse response = new NoScheduleForDayResponse(date);
        String noScheduleMessage = "no schedule!";
        doReturn(Collections.singletonList(noScheduleMessage)).when(formatter).format(response);

        assertThat(response.formatWith(formatter), contains(noScheduleMessage));
    }

}

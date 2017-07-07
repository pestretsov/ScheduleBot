package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import java.util.Collections;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Calendar;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class NoScheduleForWeekResponseTest {
    @Mock
    private SchedulerFormatter formatter;

    @Test
    public void responseUses_formatter_toFormatItself() throws Exception {
        String message = "message";
        Calendar date = Calendar.getInstance();
        NoScheduleForWeekResponse noScheduleForWeekResponse = new NoScheduleForWeekResponse(date);
        given(formatter.format(noScheduleForWeekResponse)).willReturn(Collections.singletonList(message));

        assertThat(noScheduleForWeekResponse.formatWith(formatter), contains(message));
    }
}

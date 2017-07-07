package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

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
        given(formatter.getNoScheduleForWeekMessage(date)).willReturn(message);

        assertThat(new NoScheduleForWeekResponse(date).formatWith(formatter), contains(message));
    }
}

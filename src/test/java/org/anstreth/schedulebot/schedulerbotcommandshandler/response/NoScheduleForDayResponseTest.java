package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import org.anstreth.schedulebot.schedulebotservice.MessageSender;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Calendar;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NoScheduleForDayResponseTest {

    @Mock
    private SchedulerFormatter formatter;

    @Mock
    private MessageSender sender;

    @Test
    public void responseSendsProperMessage() {
        Calendar date = Calendar.getInstance();
        NoScheduleForDayResponse response = new NoScheduleForDayResponse(date);
        String noScheduleMessage = "no schedule!";
        when(formatter.getNoScheduleForDateMessage(date)).thenReturn(noScheduleMessage);

        response.formatAndSend(formatter, sender);

        verify(sender).sendMessage(noScheduleMessage);
    }

    @Test
    public void responseUses_formatter_toFormatItself() {
        Calendar date = Calendar.getInstance();
        NoScheduleForDayResponse response = new NoScheduleForDayResponse(date);
        String noScheduleMessage = "no schedule!";
        when(formatter.getNoScheduleForDateMessage(date)).thenReturn(noScheduleMessage);

        assertThat(response.format(formatter), contains(noScheduleMessage));
    }

}
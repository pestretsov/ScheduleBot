package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import org.anstreth.schedulebot.schedulebotservice.MessageSender;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Calendar;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NoScheduleForDayResponseTest {

    @Mock
    private SchedulerFormatter formatter;

    @Mock
    private MessageSender sender;

    @Test
    public void name() {
        Calendar date = Calendar.getInstance();
        NoScheduleForDayResponse response = new NoScheduleForDayResponse(date);
        String noScheduleMessage = "no schedule!";
        when(formatter.getNoScheduleForDateMessage(date)).thenReturn(noScheduleMessage);

        response.formatAndSend(formatter, sender);

        verify(sender).sendMessage(noScheduleMessage);
    }
}
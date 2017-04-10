package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import org.anstreth.schedulebot.schedulebotservice.MessageSender;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Calendar;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NoScheduleResponseTest {

    @Mock
    private SchedulerFormatter formatter;

    @Mock
    private MessageSender sender;

    @Test
    public void name() {
        Calendar date = Calendar.getInstance();
        NoScheduleResponse response = new NoScheduleResponse(date);
        String noScheduleMessage = "no schedule!";
        when(formatter.getNoScheduleForDateMessage(date)).thenReturn(noScheduleMessage);

        response.formatAndSend(formatter, sender);

        verify(sender).sendMessage(noScheduleMessage);
    }
}
package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import org.anstreth.schedulebot.schedulebotservice.MessageSender;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Calendar;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class NoScheduleForWeekResponseTest {
    @Mock
    private SchedulerFormatter formatter;

    @Mock
    private MessageSender sender;

    @Test
    public void responseShouldTake_noScheduleForWeek_messageFromFormatterAndSendIt() {
        String message = "message";
        Calendar date = Calendar.getInstance();
        given(formatter.getNoScheduleForWeekMessage(date)).willReturn(message);

        new NoScheduleForWeekResponse(date).formatAndSend(formatter, sender);

        then(sender).should().sendMessage(message);
    }
}
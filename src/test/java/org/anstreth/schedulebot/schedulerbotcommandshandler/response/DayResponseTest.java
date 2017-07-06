package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import org.anstreth.ruzapi.response.Day;
import org.anstreth.schedulebot.schedulebotservice.MessageSender;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DayResponseTest {

    @Mock
    private SchedulerFormatter formatter;
    @Mock
    private MessageSender sender;

    @Test
    public void dayResponseTakesDayToConstructor_formatsIt_sendsIt() {
        Day day = new Day();
        DayResponse response = new DayResponse(day);
        String formattedDay = "formatted day";
        when(formatter.formatDay(day)).thenReturn(formattedDay);

        response.formatAndSend(formatter, sender);

        verify(sender).sendMessage(formattedDay);
    }

    @Test
    public void dayResponseShouldUseFormatterToFormatItself() throws Exception {
        Day day = new Day();
        DayResponse response = new DayResponse(day);
        String formattedDay = "formatted day";
        when(formatter.formatDay(day)).thenReturn(formattedDay);

        assertThat(response.format(formatter), contains(formattedDay));
    }
}
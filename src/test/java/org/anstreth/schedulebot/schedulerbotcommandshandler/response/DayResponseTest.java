package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import java.util.Collections;
import org.anstreth.ruzapi.response.Day;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DayResponseTest {

    @Mock
    private SchedulerFormatter formatter;

    @Test
    public void dayResponseShouldUseFormatterToFormatItself() throws Exception {
        DayResponse response = new DayResponse(new Day());
        String formattedDay = "formatted day";
        when(formatter.format(response)).thenReturn(Collections.singletonList(formattedDay));

        assertThat(response.formatWith(formatter), contains(formattedDay));
    }
}

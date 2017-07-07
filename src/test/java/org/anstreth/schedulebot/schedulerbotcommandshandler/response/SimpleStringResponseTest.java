package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import java.util.Collections;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class SimpleStringResponseTest {

    @Mock
    private SchedulerFormatter schedulerFormatter;

    @Test
    public void responseUses_formatter_toFormatItself() throws Exception {
        String message = "message";
        String formattedMessage = "formatted";
        SimpleStringResponse response = new SimpleStringResponse(message);
        doReturn(Collections.singletonList(formattedMessage)).when(schedulerFormatter).format(response);

        Assert.assertThat(response.formatWith(schedulerFormatter), contains(formattedMessage));
    }
}

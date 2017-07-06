package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import org.anstreth.schedulebot.schedulebotservice.MessageSender;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class SimpleStringResponseTest {

    @Mock
    private MessageSender sender;

    @Test
    public void responsePassesMessageToMessageSender() {
        String message = "message";
        SimpleStringResponse response = new SimpleStringResponse(message);

        response.formatAndSend(null, sender);

        verify(sender).sendMessage(message);
    }

    @Test
    public void responseReturnsItMessage() throws Exception {
        String message = "message";
        SimpleStringResponse response = new SimpleStringResponse(message);

        Assert.assertThat(response.format(null), contains(message));
    }
}
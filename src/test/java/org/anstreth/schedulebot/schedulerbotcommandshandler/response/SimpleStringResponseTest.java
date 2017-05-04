package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import org.anstreth.schedulebot.schedulebotservice.MessageSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
}
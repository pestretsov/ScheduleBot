package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.contains;

@RunWith(MockitoJUnitRunner.class)
public class SimpleStringResponseTest {

    @Test
    public void responseReturnsItMessage() throws Exception {
        String message = "message";
        SimpleStringResponse response = new SimpleStringResponse(message);

        Assert.assertThat(response.format(null), contains(message));
    }
}
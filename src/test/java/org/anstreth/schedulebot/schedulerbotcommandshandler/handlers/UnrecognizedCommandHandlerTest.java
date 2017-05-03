package org.anstreth.schedulebot.schedulerbotcommandshandler.handlers;

import org.anstreth.schedulebot.schedulerbotcommandshandler.response.ScheduleResponse;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.SimpleStringResponse;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class UnrecognizedCommandHandlerTest {
    @Test
    public void handlerReturnsPlainStringResponseWithMessage() {
        ScheduleResponse handle = new UnrecognizedCommandHandler().handle(null);

        assertThat(handle, is(instanceOf(SimpleStringResponse.class)));
    }
}
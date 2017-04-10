package org.anstreth.schedulebot.schedulerbotcommandshandler;

import org.anstreth.schedulebot.schedulebotservice.MessageSender;
import org.anstreth.schedulebot.schedulerbotcommandshandler.handlers.ScheduleRequestHandlersSupplier;
import org.anstreth.schedulebot.schedulerbotcommandshandler.handlers.SchedulerRequestHandler;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SchedulerBotCommandsHandlerTest {

    private SchedulerBotCommandsHandler schedulerBotCommandsHandler;

    @Mock
    private MessageSender sender;

    @Mock
    private ScheduleRequestHandlersSupplier scheduleRequestHandlersSupplier;

    @Mock
    private SchedulerRequestHandler mockRequestHandler;

    @Before
    public void initService() {
        schedulerBotCommandsHandler = new SchedulerBotCommandsHandler(scheduleRequestHandlersSupplier);
    }

    @Test
    public void commandsHandlerTakesHandlerFromSupplierAndAsksHimToHandleRequest() {
        int groupId = 2;
        String command = "command";
        ScheduleRequest requestToHandle = new ScheduleRequest(groupId, command);
        when(scheduleRequestHandlersSupplier.getHandlerForCommand(command)).thenReturn(mockRequestHandler);

        schedulerBotCommandsHandler.handleRequest(requestToHandle, sender);
    }
}
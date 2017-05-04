package org.anstreth.schedulebot.schedulerbotcommandshandler;

import org.anstreth.schedulebot.schedulebotservice.MessageSender;
import org.anstreth.schedulebot.schedulerbotcommandshandler.handlers.ScheduleRequestHandlersRouter;
import org.anstreth.schedulebot.schedulerbotcommandshandler.handlers.SchedulerRequestHandler;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.ScheduleResponse;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SchedulerBotCommandsHandlerTest {

    @InjectMocks
    private SchedulerBotCommandsHandler schedulerBotCommandsHandler;

    @Mock
    private SchedulerFormatter formatter;

    @Mock
    private MessageSender sender;

    @Mock
    private ScheduleRequestHandlersRouter scheduleRequestHandlersRouter;

    @Mock
    private SchedulerRequestHandler mockRequestHandler;

    @Mock
    private ScheduleResponse reponseToRequest;

    @Test
    public void commandsHandlerTakesHandlerFromSupplierAndAsksHimToHandleRequest() {
        int groupId = 2;
        String command = "command";
        ScheduleRequest requestToHandle = new ScheduleRequest(groupId, command);
        when(scheduleRequestHandlersRouter.getHandlerForCommand(command)).thenReturn(mockRequestHandler);
        when(mockRequestHandler.handle(requestToHandle)).thenReturn(reponseToRequest);

        schedulerBotCommandsHandler.handleRequest(requestToHandle, sender);

        verify(reponseToRequest).formatAndSend(formatter, sender);
    }
}
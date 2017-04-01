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
        String expectedMessageToSend = "response";
        ScheduleRequest requestToHandle = new ScheduleRequest(groupId, command);
        when(scheduleRequestHandlersSupplier.getHandlerForCommand(command)).thenReturn(mockRequestHandler);
        when(mockRequestHandler.handle(requestToHandle)).thenReturn(expectedMessageToSend);

        schedulerBotCommandsHandler.handleRequest(requestToHandle, sender);

        verify(sender).sendMessage(expectedMessageToSend);
    }
//
//    @Test
//    @Ignore
//    public void ifRepositoryThrowsNoScheduleExceptionThenNoScheduleForDateMessageIsReturned() throws Exception {
//        int groupId = 2;
//        String noScheduleMessage = "no schedule message";
//        when(formatter.getNoScheduleForDateMessage(any(Calendar.class))).thenReturn(noScheduleMessage);
//        when(schedulerRepository.getScheduleForGroupForDay(eq(groupId), any(Calendar.class))).thenThrow(new NoScheduleForDay());
//
//        schedulerBotCommandsHandler.handleRequest(new ScheduleRequest(groupId, "/today"), sender);
//
//        verify(sender).sendMessage(noScheduleMessage);
//    }
}
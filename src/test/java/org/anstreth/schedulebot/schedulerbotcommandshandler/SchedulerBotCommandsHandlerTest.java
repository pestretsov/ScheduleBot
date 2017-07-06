package org.anstreth.schedulebot.schedulerbotcommandshandler;

import org.anstreth.schedulebot.commands.ScheduleCommand;
import org.anstreth.schedulebot.schedulebotservice.MessageSender;
import org.anstreth.schedulebot.schedulerbotcommandshandler.handlers.ScheduleRequestHandlersRouter;
import org.anstreth.schedulebot.schedulerbotcommandshandler.handlers.SchedulerRequestHandler;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.ScheduleResponse;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SchedulerBotCommandsHandlerTest {

    @InjectMocks
    private SchedulerBotCommandsHandler schedulerBotCommandsHandler;

    @Mock
    private SchedulerFormatter formatter;

    @Mock
    private ScheduleRequestHandlersRouter scheduleRequestHandlersRouter;

    @Mock
    private SchedulerRequestHandler mockRequestHandler;

    @Mock
    private ScheduleResponse reponseToRequest;

    @Mock
    private MessageSender sender;

    @Test
    public void commandsHandler_TakesHandlerFromRouter_ThenFormatsWith_Formatter_thenSends() {
        int groupId = 2;
        ScheduleCommand command = ScheduleCommand.WEEK;
        List<String> formattedMessages = Arrays.asList("one", "two");
        ScheduleRequest requestToHandle = new ScheduleRequest(groupId, command);
        when(scheduleRequestHandlersRouter.getHandlerForCommand(command)).thenReturn(mockRequestHandler);
        when(mockRequestHandler.handle(requestToHandle)).thenReturn(reponseToRequest);
        when(reponseToRequest.format(formatter)).thenReturn(formattedMessages);

        schedulerBotCommandsHandler.handleRequest(requestToHandle, sender);

        InOrder inOrder = inOrder(sender);
        inOrder.verify(sender).sendMessage("one");
        inOrder.verify(sender).sendMessage("two");
    }
}
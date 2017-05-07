package org.anstreth.schedulebot.schedulerbotcommandshandler;

import org.anstreth.schedulebot.schedulebotservice.MessageSender;
import org.anstreth.schedulebot.schedulebotservice.MessageWithRepliesSender;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SchedulerBotCommandsHandlerTest {

    @InjectMocks
    private SchedulerBotCommandsHandler schedulerBotCommandsHandler;

    @Mock
    private SchedulerFormatter formatter;

    @Mock
    private MessageWithRepliesSender sender;

    @Mock
    private ScheduleRequestHandlersRouter scheduleRequestHandlersRouter;

    @Mock
    private SchedulerRequestHandler mockRequestHandler;

    @Mock
    private ScheduleResponse reponseToRequest;

    @Mock
    private MessageSender senderWithReplies;

    private List<String> repliesToAdd = Arrays.asList("/today", "/tomorrow", "/week");

    @Test
    public void commandsHandler_TakesHandlerFromRouter_AndAsksHimToHandleRequest_withCertainReplies() {
        int groupId = 2;
        String command = "command";
        ScheduleRequest requestToHandle = new ScheduleRequest(groupId, command);
        when(scheduleRequestHandlersRouter.getHandlerForCommand(command)).thenReturn(mockRequestHandler);
        when(mockRequestHandler.handle(requestToHandle)).thenReturn(reponseToRequest);
        when(sender.withReplies(repliesToAdd)).thenReturn(senderWithReplies);

        schedulerBotCommandsHandler.handleRequest(requestToHandle, sender);

        verify(reponseToRequest).formatAndSend(formatter, senderWithReplies);
    }
}
package org.anstreth.schedulebot.schedulerbotcommandshandler;

import org.anstreth.schedulebot.exceptions.NoScheduleForDay;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.anstreth.schedulebot.schedulerrepository.SchedulerRepository;
import org.anstreth.schedulebot.schedulebotservice.MessageSender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Calendar;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SchedulerBotCommandsHandlerTest {

    private SchedulerBotCommandsHandler schedulerBotCommandsHandler;

    @Mock
    private MessageSender sender;

    @Mock
    private SchedulerRepository schedulerRepository;

    @Mock
    private SchedulerFormatter formatter;

    @Before
    public void initService() {
        schedulerBotCommandsHandler = new SchedulerBotCommandsHandler(schedulerRepository, formatter);
    }

    @Test
    public void ifRepositoryThrowsNoScheduleExceptionThenNoScheduleForDateMessageIsReturned() throws Exception {
        int groupId = 2;
        String noScheduleMessage = "no schedule message";
        when(formatter.getNoScheduleForDateMessage(any(Calendar.class))).thenReturn(noScheduleMessage);
        when(schedulerRepository.getScheduleForGroupForDay(eq(groupId), any(Calendar.class))).thenThrow(new NoScheduleForDay());

        schedulerBotCommandsHandler.handleRequest(new ScheduleRequest(groupId, "/today"), sender);

        verify(sender).sendMessage(noScheduleMessage);
    }
}
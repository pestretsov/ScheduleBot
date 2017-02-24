package org.anstreth.schedulebot;

import org.anstreth.schedulebot.exceptions.NoScheduleForDay;
import org.anstreth.schedulebot.schedulebottextservice.MessageSender;
import org.anstreth.schedulebot.schedulebottextservice.SchedulerBotTextService;
import org.anstreth.schedulebot.schedulebottextservice.request.UserRequest;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.anstreth.schedulebot.schedulerrepository.SchedulerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Calendar;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SchedulerBotTextServiceTest {

    @InjectMocks
    private SchedulerBotTextService schedulerBotTextService;

    @Mock
    private MessageSender sender;

    @Mock
    private SchedulerRepository repository;

    @Mock
    private SchedulerFormatter formatter;

    @Test
    public void ifRepositoryThrowsNoScheduleExceptionThenNoScheduleForDateMessageIsReturned() throws Exception {
        String noScheduleMessage = "no schedule message";
        when(formatter.getNoScheduleForDateMessage(any(Calendar.class))).thenReturn(noScheduleMessage);
        when(repository.getScheduleForDay(any(Calendar.class))).thenThrow(new NoScheduleForDay());

        schedulerBotTextService.handleText(new UserRequest(1L, "/today"), sender);

        verify(sender).sendMessage(noScheduleMessage);
    }
}
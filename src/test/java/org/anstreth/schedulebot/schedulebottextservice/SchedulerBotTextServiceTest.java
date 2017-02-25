package org.anstreth.schedulebot.schedulebottextservice;

import org.anstreth.schedulebot.exceptions.NoScheduleForDay;
import org.anstreth.schedulebot.schedulebottextservice.request.UserRequest;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.anstreth.schedulebot.schedulerrepository.SchedulerRepository;
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
public class SchedulerBotTextServiceTest {

    private SchedulerBotTextService schedulerBotTextService;

    private int groupId = 1;

    @Mock
    private MessageSender sender;

    @Mock
    private SchedulerRepository repository;

    @Mock
    private SchedulerFormatter formatter;

    @Before
    public void initService() {
        schedulerBotTextService = new SchedulerBotTextService(groupId, repository, formatter);
    }

    @Test
    public void ifRepositoryThrowsNoScheduleExceptionThenNoScheduleForDateMessageIsReturned() throws Exception {
        String noScheduleMessage = "no schedule message";
        when(formatter.getNoScheduleForDateMessage(any(Calendar.class))).thenReturn(noScheduleMessage);
        when(repository.getScheduleForGroupForDay(eq(groupId), any(Calendar.class))).thenThrow(new NoScheduleForDay());

        schedulerBotTextService.handleText(new UserRequest(1L, "/today"), sender);

        verify(sender).sendMessage(noScheduleMessage);
    }
}
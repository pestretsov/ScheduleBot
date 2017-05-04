package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulerbotcommandshandler.SchedulerBotCommandsHandler;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerrepository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SchedulerBotServiceTest {
    private SchedulerBotService schedulerBotService;

    @Mock
    private SchedulerBotCommandsHandler schedulerBotCommandsHandler;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessageSender messageSender;

    @Mock
    private UserManager userManager;

    @Before
    public void setUp() throws Exception {
        schedulerBotService = new SchedulerBotService(userManager, schedulerBotCommandsHandler);
    }

    @Test
    public void ifUserManagerReturnsEmptyGroupIdRequestIsRedirectedToUserManager() throws Exception {
        long userId = 1L;
        String requestMessage = "message";
        when(userManager.getGroupIdOfUser(userId)).thenReturn(Optional.empty());

        schedulerBotService.handleRequest(new UserRequest(userId, requestMessage), messageSender);

        verify(userManager).handleUserAbsense(new UserRequest(userId, requestMessage), messageSender);
        verifyZeroInteractions(messageSender, schedulerBotCommandsHandler, userRepository);

    }

    @Test
    public void ifUserManagerReturnsGroupNumberItIsPassedToCommandsHandler() throws Exception {
        long userId = 1L;
        String requestMessage = "message";
        int groupId = 2;
        when(userManager.getGroupIdOfUser(userId)).thenReturn(Optional.of(groupId));

        schedulerBotService.handleRequest(new UserRequest(userId, requestMessage), messageSender);

        verify(schedulerBotCommandsHandler).handleRequest(new ScheduleRequest(groupId, requestMessage), messageSender);
    }

}
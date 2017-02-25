package org.anstreth.schedulebot.scheduleuserservice;

import org.anstreth.schedulebot.model.User;
import org.anstreth.schedulebot.schedulerbotcommandshandler.SchedulerBotCommandsHandler;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerrepository.UserRepository;
import org.anstreth.schedulebot.scheduleuserservice.request.UserRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SchedulerUserServiceTest {
    private SchedulerUserService schedulerUserService;
    private int groupId = 21811;

    @Mock
    private SchedulerBotCommandsHandler schedulerBotCommandsHandler;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessageSender messageSender;

    @Before
    public void setUp() throws Exception {
        schedulerUserService = new SchedulerUserService(groupId, userRepository, schedulerBotCommandsHandler);
    }

    @Test
    public void inRepositoryReturnsNullStandartGroupIdIsSettled() throws Exception {
        String requestMessage = "message";
        schedulerUserService.handleRequest(new UserRequest(1L, requestMessage), messageSender);

        verify(schedulerBotCommandsHandler).handleRequest(new ScheduleRequest(groupId, requestMessage), messageSender);
    }

    @Test
    public void ifUserRepositoryReturnsUserItsGroupIdIsPassedToCommandsHandler() throws Exception {
        String requestMessage = "message";
        long userId = 1L;
        int groupId = 2;
        User user = new User(userId, groupId);
        when(userRepository.getUserById(userId)).thenReturn(user);

        schedulerUserService.handleRequest(new UserRequest(userId, requestMessage), messageSender);

        ScheduleRequest expectedRequest = new ScheduleRequest(groupId, requestMessage);
        verify(schedulerBotCommandsHandler).handleRequest(expectedRequest, messageSender);
    }
}
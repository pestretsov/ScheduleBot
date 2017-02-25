package org.anstreth.schedulebot.scheduleuserservice;

import org.anstreth.schedulebot.model.User;
import org.anstreth.schedulebot.schedulerbotcommandshandler.SchedulerBotCommandsHandler;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerrepository.UserRepository;
import org.anstreth.schedulebot.scheduleuserservice.request.UserRequest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SchedulerUserServiceTest {
    private SchedulerUserService schedulerUserService;

    @Mock
    private SchedulerBotCommandsHandler schedulerBotCommandsHandler;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessageSender messageSender;

    @Before
    public void setUp() throws Exception {
        schedulerUserService = new SchedulerUserService(userRepository, schedulerBotCommandsHandler);
    }

    @Test
    public void ifUserRepositoryReturnsNullNewUserIsCreatedAndBotAsksForGroupName() throws Exception {
        long userId = 1L;
        String requestMessage = "message";
        String noGroupSpecifiedMessage = "Send me your group number like '12345/6' to get your schedule.";
        when(userRepository.getUserById(userId)).thenReturn(null);

        schedulerUserService.handleRequest(new UserRequest(userId, requestMessage), messageSender);

        verify(userRepository).save(new User(userId, User.NO_GROUP_SPECIFIED));
        verify(messageSender).sendMessage(noGroupSpecifiedMessage);
        verifyNoMoreInteractions(messageSender);
        verifyZeroInteractions(schedulerBotCommandsHandler);
    }

    @Test
    public void ifUserGroupIdIsNOT_SPECIFIED_VALUEThenMessageIsNotSent() throws Exception {
        long userId = 1L;
        int groupId = User.NO_GROUP_SPECIFIED;
        String requestMessage = "message";
        User user = new User(userId, groupId);
        when(userRepository.getUserById(userId)).thenReturn(user);

        schedulerUserService.handleRequest(new UserRequest(userId, requestMessage), messageSender);

        verifyZeroInteractions(schedulerBotCommandsHandler, messageSender);
    }

    @Test
    public void ifUserRepositoryReturnsUserItsGroupIdIsPassedToCommandsHandler() throws Exception {
        long userId = 1L;
        int groupId = 2;
        String requestMessage = "message";
        User user = new User(userId, groupId);
        when(userRepository.getUserById(userId)).thenReturn(user);

        schedulerUserService.handleRequest(new UserRequest(userId, requestMessage), messageSender);

        ScheduleRequest expectedRequest = new ScheduleRequest(groupId, requestMessage);
        verify(schedulerBotCommandsHandler).handleRequest(expectedRequest, messageSender);
    }
}
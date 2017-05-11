package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.schedulebot.commands.ScheduleCommandParser;
import org.anstreth.schedulebot.exceptions.NoGroupForUserException;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulerbotcommandshandler.SchedulerBotCommandsHandler;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.anstreth.schedulebot.commands.ScheduleCommand.UNKNOWN;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SchedulerBotServiceTest {

    @InjectMocks
    private SchedulerBotService schedulerBotService;

    @Mock
    private SchedulerBotCommandsHandler schedulerBotCommandsHandler;

    @Mock
    private MessageWithRepliesSender messageSender;

    @Mock
    private MessageSender senderWithReplies;

    @Mock
    private UserGroupManager userGroupManager;

    @Mock
    private ScheduleCommandParser scheduleCommandParser;

    @Mock
    private UserGroupSearchService userGroupSearchService;

    private final List<String> possibleReplies = Arrays.asList("Today", "Tomorrow", "Week");

    @Test
    public void ifUserManagerReturnsEmptyGroupIdRequestIsRedirectedToUserManager() throws Exception {
        long userId = 1L;
        String requestMessage = "message";
        when(userGroupManager.getGroupIdOfUser(userId)).thenReturn(Optional.empty());

        schedulerBotService.handleRequest(new UserRequest(userId, requestMessage), messageSender);

        verify(userGroupManager).handleUserAbsense(new UserRequest(userId, requestMessage), messageSender);
        verifyZeroInteractions(messageSender, schedulerBotCommandsHandler);
    }

    @Test
    public void ifUserManagerReturnsGroupNumberItIsPassedToCommandsHandler() throws Exception {
        long userId = 1L;
        int groupId = 2;

        String requestMessage = "message";
        when(userGroupManager.getGroupIdOfUser(userId)).thenReturn(Optional.of(groupId));
        when(scheduleCommandParser.parse(requestMessage)).thenReturn(UNKNOWN);
        when(messageSender.withReplies(possibleReplies)).thenReturn(senderWithReplies);

        schedulerBotService.handleRequest(new UserRequest(userId, requestMessage), messageSender);

        verify(schedulerBotCommandsHandler).handleRequest(new ScheduleRequest(groupId, UNKNOWN), senderWithReplies);
    }

    @Test
    public void if_userGroupManager_returnsGroupIdThen_ScheduleRequest_and_sender_withRepliesArePassedToHandler() {
        long userId = 1;
        int groupId = 2;
        String message = "message";
        doReturn(UNKNOWN).when(scheduleCommandParser).parse(message);
        doReturn(groupId).when(userGroupManager).getGroupIdOfUserWithExceptions(userId);
        doReturn(senderWithReplies).when(messageSender).withReplies(possibleReplies);

        schedulerBotService.otherHandleRequest(new UserRequest(userId, message), messageSender);

        then(schedulerBotCommandsHandler).should().handleRequest(new ScheduleRequest(groupId, UNKNOWN), senderWithReplies);
    }

    @Test
    public void if_userGroupManager_throws_NoGroupForUserException_then_UserGroupSearchService_isCalled() {
        long userId = 1;
        String message = "message";
        UserRequest userRequest = new UserRequest(userId, message);
        doThrow(NoGroupForUserException.class).when(userGroupManager).getGroupIdOfUserWithExceptions(userId);

        schedulerBotService.otherHandleRequest(userRequest, messageSender);

        then(userGroupSearchService).should().tryToFindUserGroup(userRequest, messageSender, possibleReplies);
    }
}
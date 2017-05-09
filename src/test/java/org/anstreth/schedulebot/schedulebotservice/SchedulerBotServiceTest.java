package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.schedulebot.commands.ScheduleCommand;
import org.anstreth.schedulebot.commands.ScheduleCommandParser;
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
        when(scheduleCommandParser.parse(requestMessage)).thenReturn(ScheduleCommand.UNKNOWN);
        when(messageSender.withReplies(possibleReplies)).thenReturn(senderWithReplies);

        schedulerBotService.handleRequest(new UserRequest(userId, requestMessage), messageSender);

        verify(schedulerBotCommandsHandler).handleRequest(new ScheduleRequest(groupId, ScheduleCommand.UNKNOWN), senderWithReplies);
    }

}
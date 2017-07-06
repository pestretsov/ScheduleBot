package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.schedulebot.commands.ScheduleCommandParser;
import org.anstreth.schedulebot.exceptions.NoGroupForUserException;
import org.anstreth.schedulebot.exceptions.NoSuchUserException;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulerbotcommandshandler.SchedulerBotCommandsHandler;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.anstreth.schedulebot.commands.ScheduleCommand.UNKNOWN;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;

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

    @Mock
    private UserCreationService userCreationService;

    private final List<String> possibleReplies = Arrays.asList("Today", "Tomorrow", "Week");

    @Test
    public void if_userGroupManager_returnsGroupIdThen_ScheduleRequest_and_sender_withRepliesArePassedToHandler() {
        long userId = 1;
        int groupId = 2;
        String message = "message";
        List<String> scheduleMessages = Arrays.asList("one", "two");
        ScheduleRequest scheduleRequest = new ScheduleRequest(groupId, UNKNOWN);
        doReturn(UNKNOWN).when(scheduleCommandParser).parse(message);
        doReturn(groupId).when(userGroupManager).getGroupIdOfUser(userId);
        doReturn(senderWithReplies).when(messageSender).withReplies(possibleReplies);
        doReturn(scheduleMessages).when(schedulerBotCommandsHandler).handleRequest(scheduleRequest);

        schedulerBotService.handleRequest(new UserRequest(userId, message), messageSender);

        InOrder inOrder = inOrder(senderWithReplies);
        then(senderWithReplies).should(inOrder).sendMessage("one");
        then(senderWithReplies).should(inOrder).sendMessage("two");
    }

    @Test
    public void if_userGroupManager_throws_NoGroupForUserException_then_UserGroupSearchService_isCalled() {
        long userId = 1;
        String message = "message";
        UserRequest userRequest = new UserRequest(userId, message);
        doThrow(NoGroupForUserException.class).when(userGroupManager).getGroupIdOfUser(userId);

        schedulerBotService.handleRequest(userRequest, messageSender);

        then(userGroupSearchService).should().tryToFindUserGroup(userRequest, messageSender, possibleReplies);
    }

    @Test
    public void if_userGroupManager_throws_NoSuchUserException_then_UserCreatorService_isCalled() {
        long userId = 1;
        String message = "message";
        UserRequest userRequest = new UserRequest(userId, message);
        doThrow(NoSuchUserException.class).when(userGroupManager).getGroupIdOfUser(userId);

        schedulerBotService.handleRequest(userRequest, messageSender);

        then(userCreationService).should().createUserAndAskForGroup(userRequest, messageSender);
    }
}
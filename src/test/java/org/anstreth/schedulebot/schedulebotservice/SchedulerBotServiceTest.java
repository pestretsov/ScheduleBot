package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.schedulebot.commands.ScheduleCommandParser;
import org.anstreth.schedulebot.exceptions.NoGroupForUserException;
import org.anstreth.schedulebot.exceptions.NoSuchUserException;
import org.anstreth.schedulebot.response.BotResponse;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulebotservice.user.UserCreationService;
import org.anstreth.schedulebot.schedulebotservice.user.UserGroupManager;
import org.anstreth.schedulebot.schedulebotservice.user.UserGroupSearchService;
import org.anstreth.schedulebot.schedulebotservice.user.UserStateManager;
import org.anstreth.schedulebot.schedulerbotcommandshandler.SchedulerBotCommandsHandler;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerrepository.UserRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.anstreth.schedulebot.commands.ScheduleCommand.UNKNOWN;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SchedulerBotServiceTest {

    @InjectMocks
    private SchedulerBotService schedulerBotService;

    @Mock
    private SchedulerBotCommandsHandler schedulerBotCommandsHandler;

    @Mock
    private UserGroupManager userGroupManager;

    @Mock
    private ScheduleCommandParser scheduleCommandParser;

    @Mock
    private UserGroupSearchService userGroupSearchService;

    @Mock
    private UserCreationService userCreationService;

    @Mock
    private UserStateManager userStateManager;

    private final List<String> possibleReplies = Arrays.asList("Today", "Tomorrow", "Week");

    @Mock
    private UserRepository userRepository;

    @Test
    public void if_userRepository_returnsNull_thenUserIsCreatedAndAskedForGroup() throws Exception {
        long userId = 1;
        UserRequest request = new UserRequest(userId, "command");
        doReturn(null).when(userRepository).getUserById(userId);

        assertThat(
                schedulerBotService.handleRequest(request),
                is(new BotResponse("Send me your group number like '12345/6' to get your schedule."))
        );

        then(userCreationService).should().createNewUser(request);
        then(userStateManager).should().transitToAskedForGroup(userId);
    }

    @Test
    @Ignore
    public void if_userGroupManager_returnsGroupIdThen_ScheduleRequest_and_sender_withRepliesArePassedToHandler() {
        long userId = 1;
        int groupId = 2;
        String message = "message";
        List<String> scheduleMessages = Arrays.asList("one", "two");
        ScheduleRequest scheduleRequest = new ScheduleRequest(groupId, UNKNOWN);
        doReturn(UNKNOWN).when(scheduleCommandParser).parse(message);
        doReturn(groupId).when(userGroupManager).getGroupIdOfUser(userId);
        doReturn(scheduleMessages).when(schedulerBotCommandsHandler).handleRequest(scheduleRequest);

        assertThat(
                schedulerBotService.handleRequest(new UserRequest(userId, message)),
                is(new BotResponse(scheduleMessages, possibleReplies))
        );
    }

    @Test
    @Ignore
    public void if_userGroupManager_throws_NoGroupForUserException_then_UserGroupSearchService_isCalled() {
        long userId = 1;
        String message = "message";
        UserRequest userRequest = new UserRequest(userId, message);
        doThrow(NoGroupForUserException.class).when(userGroupManager).getGroupIdOfUser(userId);
        BotResponse responseFromGroupSearchService = mock(BotResponse.class);
        doReturn(responseFromGroupSearchService).when(userGroupSearchService).tryToFindUserGroup(userRequest, possibleReplies);

        assertThat(schedulerBotService.handleRequest(userRequest), is(responseFromGroupSearchService));
    }

    @Test
    @Ignore
    public void if_userGroupManager_throws_NoSuchUserException_then_UserCreatorService_isCalled() {
        long userId = 1;
        String message = "message";
        UserRequest userRequest = new UserRequest(userId, message);
        doThrow(NoSuchUserException.class).when(userGroupManager).getGroupIdOfUser(userId);

        assertThat(schedulerBotService.handleRequest(userRequest), is(new BotResponse("Send me your group number like '12345/6' to get your schedule.")));

        then(userCreationService).should().createNewUser(userRequest);
        then(userStateManager).should().transitToAskedForGroup(userId);
    }
}

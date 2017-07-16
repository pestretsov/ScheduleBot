package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.ruzapi.response.Group;
import org.anstreth.schedulebot.commands.UserCommand;
import org.anstreth.schedulebot.commands.UserCommandParser;
import org.anstreth.schedulebot.model.User;
import org.anstreth.schedulebot.response.BotResponse;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulebotservice.user.UserCreationService;
import org.anstreth.schedulebot.schedulebotservice.user.UserStateManager;
import org.anstreth.schedulebot.schedulerbotcommandshandler.SchedulerBotCommandsHandler;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerrepository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.anstreth.schedulebot.commands.UserCommand.TODAY;
import static org.anstreth.schedulebot.model.UserState.*;
import static org.anstreth.schedulebot.response.PossibleReplies.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class SchedulerBotServiceTest {

    @InjectMocks
    private SchedulerBotService schedulerBotService;

    @Mock
    private SchedulerBotCommandsHandler schedulerBotCommandsHandler;

    @Mock
    private UserCommandParser userCommandParser;

    @Mock
    private UserCreationService userCreationService;

    @Mock
    private UserStateManager userStateManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GroupSearcher groupSearcher;

    @Mock
    private SchedulerBotMenu schedulerBotMenu;

    @Test
    public void if_userRepository_returnsNull_thenUserIsCreatedAndAskedForGroup() throws Exception {
        long userId = 1;
        UserRequest request = new UserRequest(userId, "command");
        doReturn(null).when(userRepository).getUserById(userId);
        User createdUser = new User(userId, 1, NO_GROUP);
        User userAskedForGroup = new User(userId, 1, ASKED_FOR_GROUP);
        doReturn(createdUser).when(userCreationService).createNewUser(userId);
        doReturn(userAskedForGroup).when(userStateManager).transitToAskedForGroup(createdUser);

        assertThat(
                schedulerBotService.handleRequest(request),
                is(new BotResponse("Send me your group number like '12345/6' to get your schedule."))
        );
    }

    @Test
    public void whenUserStateIs_WITH_GROUP_thenRequestIsPassedTo_commandsHandler() throws Exception {
        long userId = 1;
        int groupId = 2;
        String command = "command";
        UserRequest request = new UserRequest(userId, command);
        doReturn(new User(userId, groupId, WITH_GROUP)).when(userRepository).getUserById(userId);
        doReturn(TODAY).when(userCommandParser).parse(command);
        List<String> scheduleMessages = Arrays.asList("one", "two");
        doReturn(scheduleMessages)
                .when(schedulerBotCommandsHandler).handleRequest(new ScheduleRequest(groupId, TODAY));

        assertThat(
                schedulerBotService.handleRequest(request),
                is(new BotResponse(scheduleMessages, WITH_GROUP_REPLIES))
        );
    }

    @Test
    public void whenUserStateIs_ASKED_FOR_GROUP_andHisGroupIsFoundThenSuccess_BotResponse_returned() throws Exception {
        long userId = 1;
        int groupId = 0;
        int foundGroupId = 3;
        String command = "command";
        UserRequest request = new UserRequest(userId, command);
        doReturn(new User(userId, groupId, ASKED_FOR_GROUP))
                .when(userRepository).getUserById(userId);
        doReturn(Optional.of(new Group(foundGroupId, "groupName", "spec")))
                .when(groupSearcher).findGroupByName(command);

        assertThat(
                schedulerBotService.handleRequest(request),
                is(new BotResponse("Your group is set to 'groupName'.", WITH_GROUP_REPLIES))
        );

        then(userRepository).should().save(new User(userId, foundGroupId, WITH_GROUP));
    }


    @Test
    public void whenUserStateIs_ASKED_FOR_GROUP_andNoneIsFoundErrorMessageIsReturned() throws Exception {
        long userId = 1;
        int groupId = 0;
        String command = "command";
        UserRequest request = new UserRequest(userId, command);
        doReturn(new User(userId, groupId, ASKED_FOR_GROUP)).when(userRepository).getUserById(userId);
        doReturn(Optional.empty()).when(groupSearcher).findGroupByName(command);

        assertThat(schedulerBotService.handleRequest(request),
                is(new BotResponse("No group by name 'command' is found! Try again.")));
    }

    @Test
    public void ifUserStateIs_WITH_GROUP_andCommandIsParsedAs_MENU_ThenItsStateIsChangedTo_MENU() throws Exception {
        long userId = 1;
        int groupId = 2;
        String command = "command";
        UserRequest menuRequest = new UserRequest(userId, command);
        doReturn(new User(userId, groupId, WITH_GROUP)).when(userRepository).getUserById(userId);
        doReturn(UserCommand.MENU).when(userCommandParser).parse(command);
        BotResponse transitToMenuResponse = new BotResponse("What do you want to do?", MENU_REPLIES);

        assertThat(schedulerBotService.handleRequest(menuRequest), is(transitToMenuResponse));

        then(userStateManager).should().transitToMenu(userId);
    }

    @Test
    public void whenUserStateIs_MENU_thenRequestIsPassedTo_SchedulerBotMenu() throws Exception {
        long userId = 1;
        int groupId = 0;
        String command = "command";
        UserRequest request = new UserRequest(userId, command);
        BotResponse menuResponse = mock(BotResponse.class);
        doReturn(new User(userId, groupId, MENU)).when(userRepository).getUserById(userId);
        doReturn(menuResponse).when(schedulerBotMenu).handleRequest(request);

        assertThat(schedulerBotService.handleRequest(request),
                is(menuResponse));
    }
}

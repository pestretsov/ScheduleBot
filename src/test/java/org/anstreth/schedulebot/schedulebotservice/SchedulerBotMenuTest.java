package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.schedulebot.commands.MenuCommandParser;
import org.anstreth.schedulebot.response.BotResponse;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulebotservice.user.UserStateManager;
import org.anstreth.schedulebot.schedulerrepository.UserGroupRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.anstreth.schedulebot.commands.MenuCommand.*;
import static org.anstreth.schedulebot.response.PossibleReplies.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class SchedulerBotMenuTest {

    @InjectMocks
    private SchedulerBotMenu schedulerBotMenu;

    @Mock
    private MenuCommandParser menuCommandParser;
    @Mock
    private UserStateManager manager;

    @Mock
    private UserGroupRepository userGroupRepository;

    @Test
    public void whenCommandIs_BACK_thenMenuMovesUserTo_HAS_GROUP_stateAndAsksForSchedule() throws Exception {
        long userId = 1;
        String command = "command";
        UserRequest backRequest = new UserRequest(userId, command);
        BotResponse askForScheduleCommand = new BotResponse("You can ask for schedule now.", WITH_GROUP_REPLIES);
        doReturn(BACK).when(menuCommandParser).parse(command);

        assertThat(schedulerBotMenu.handleRequest(backRequest), is(askForScheduleCommand));

        then(manager).should().transitToWithGroup(userId);
    }

    @Test
    public void if_RESET_GROUP_groupIsDeleted_stateSetTo_ASKED_FOR_GROUP() throws Exception {
        long userId = 1;
        String command = "command";
        UserRequest backRequest = new UserRequest(userId, command);
        BotResponse askForGroup = new BotResponse("Send me your group number like '12345/6' to get your schedule.");
        doReturn(RESET_GROUP).when(menuCommandParser).parse(command);

        assertThat(schedulerBotMenu.handleRequest(backRequest), is(askForGroup));

        then(manager).should().transitToAskedForGroup(userId);
        then(userGroupRepository).should().remove(userId);
    }

    @Test
    public void whenCommandIs_UNKNOWN_menuSaysItDontUnderstandThat() throws Exception {
        long userId = 1;
        String command = "command";
        UserRequest backRequest = new UserRequest(userId, command);
        BotResponse dontUnderstand = new BotResponse("Sorry, don't understand that!", MENU_REPLIES);
        doReturn(UNKNOWN).when(menuCommandParser).parse(command);

        assertThat(schedulerBotMenu.handleRequest(backRequest), is(dontUnderstand));
    }
}
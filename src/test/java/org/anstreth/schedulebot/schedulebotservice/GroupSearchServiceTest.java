package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.ruzapi.response.Group;
import org.anstreth.schedulebot.model.UserRoute;
import org.anstreth.schedulebot.response.BotResponse;
import org.anstreth.schedulebot.response.PossibleReplies;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulerrepository.UserGroupRepository;
import org.anstreth.schedulebot.schedulerrepository.UserRouteRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class GroupSearchServiceTest {
    @InjectMocks
    private GroupSearchService groupSearchService;

    @Mock
    private GroupSearcher groupSearcher;

    @Mock
    private UserGroupRepository userGroupRepository;

    @Mock
    private UserRouteRepository userRouteRepository;

    @Test
    public void if_group_is_found_by_message_then_user_route_set_to_HOME_and_success_response_returned() {
        long userId = 1;
        int foundGroupId = 2;
        String searchMessage = "msg";
        UserRequest request = new UserRequest(userId, searchMessage, false);
        BotResponse groupFoundResponse = new BotResponse(
                "Your group is set to 'groupName'.",
                PossibleReplies.WITH_GROUP_REPLIES
        );
        Group foundGroup = new Group(foundGroupId, "groupName", "");
        doReturn(Optional.of(foundGroup)).when(groupSearcher).findGroupByName(searchMessage);

        assertThat(groupSearchService.handleRequest(request), is(groupFoundResponse));

        then(userRouteRepository).should().save(userId, UserRoute.HOME);
        then(userGroupRepository).should().save(userId, foundGroupId);
    }

    @Test
    public void if_group_is_not_found_then_no_group_found_response_is_returned() {
        long userId = 1;
        String searchMessage = "msg";
        UserRequest request = new UserRequest(userId, searchMessage, false);
        doReturn(Optional.empty()).when(groupSearcher).findGroupByName(searchMessage);
        BotResponse noSuchGroupResponse = new BotResponse("No group by name 'msg' is found! Try again.");

        assertThat(groupSearchService.handleRequest(request), is(noSuchGroupResponse));
    }
}
package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.ruzapi.response.Group;
import org.anstreth.schedulebot.exceptions.NoSuchGroupFoundException;
import org.anstreth.schedulebot.response.BotResponse;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulebotservice.user.UserGroupManager;
import org.anstreth.schedulebot.schedulebotservice.user.UserGroupSearchService;
import org.anstreth.schedulebot.schedulebotservice.user.UserStateManager;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class UserGroupSearchServiceTest {

    @InjectMocks
    private UserGroupSearchService userGroupSearchService;
    @Mock
    private UserGroupManager userGroupManager;

    @Mock
    private UserStateManager userStateManager;

    @Test
    public void if_UserGroupManager_findsGroup_thenSuccessMessageIsSentWithSuccessReplies() {
        long userId = 1;
        String message = "message";
        UserRequest userRequest = new UserRequest(userId, message);
        List<String> successReplies = Collections.singletonList("replies");
        Group foundGroup = new Group();
        foundGroup.setName("groupName");
        doReturn(foundGroup).when(userGroupManager).findAndSetGroupForUser(userId, message);

        Assert.assertThat(
                userGroupSearchService.tryToFindUserGroup(userRequest, successReplies),
                Matchers.is(new BotResponse("Your group is set to 'groupName'.", successReplies))
        );

        then(userStateManager).should().transitToWithGroup(userId);
    }

    @Test
    public void if_UserGroupManager_throws_NoGroupFoundException_thenFailureMessageIsSentWithoutReplies() {
        long userId = 1;
        String message = "message";
        UserRequest userRequest = new UserRequest(userId, message);
        List<String> successReplies = Collections.singletonList("replies");
        Group foundGroup = new Group();
        foundGroup.setName("groupName");
        doThrow(NoSuchGroupFoundException.class).when(userGroupManager).findAndSetGroupForUser(userId, message);

        Assert.assertThat(
                userGroupSearchService.tryToFindUserGroup(userRequest, successReplies),
                Matchers.is(new BotResponse("No group by name 'message' is found! Try again."))
        );
    }
}
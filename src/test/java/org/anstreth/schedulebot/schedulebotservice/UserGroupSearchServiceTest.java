package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.ruzapi.response.Group;
import org.anstreth.schedulebot.exceptions.NoSuchGroupFoundException;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class UserGroupSearchServiceTest {

    @InjectMocks
    private UserGroupSearchService userGroupSearchService;
    @Mock
    private UserGroupManager userGroupManager;

    @Mock
    private MessageWithRepliesSender messageSender;

    @Before
    public void setUp() throws Exception {
        doCallRealMethod().when(messageSender).withReplies(anyList());
    }

    @Test
    public void if_UserGroupManager_findsGroup_thenSuccessMessageIsSentWithSuccessReplies() {
        long userId = 1;
        String message = "message";
        UserRequest userRequest = new UserRequest(userId, message);
        List<String> successReplies = Collections.singletonList("replies");
        Group foundGroup = new Group(); foundGroup.setName("groupName");
        doReturn(foundGroup).when(userGroupManager).findAndSetGroupForUser(userId, message);

        userGroupSearchService.tryToFindUserGroup(userRequest, messageSender, successReplies);

        then(messageSender).should().sendMessage("Your group is set to 'groupName'.", successReplies);
    }

    @Test
    public void if_UserGroupManager_throws_NoGroupFoundException_thenFailureMessageIsSentWithoutReplies() {
        long userId = 1;
        String message = "message";
        UserRequest userRequest = new UserRequest(userId, message);
        List<String> successReplies = Collections.singletonList("replies");
        Group foundGroup = new Group(); foundGroup.setName("groupName");
        doThrow(NoSuchGroupFoundException.class).when(userGroupManager).findAndSetGroupForUser(userId, message);

        userGroupSearchService.tryToFindUserGroup(userRequest, messageSender, successReplies);

        then(messageSender).should().sendMessage("No group by name 'message' is found! Try again.");
    }
}
package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.schedulebot.model.User;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulebotservice.user.UserCreationService;
import org.anstreth.schedulebot.schedulebotservice.user.UserGroupManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class UserCreationServiceTest {

    @InjectMocks
    private UserCreationService userCreationService;

    @Mock
    private UserGroupManager userGroupManager;

    @Test
    public void serviceCreatesUserAndAsksForGroup() {
        long userId = 1;
        User createdUser = mock(User.class);
        UserRequest userRequest = new UserRequest(userId, "message");
        doReturn(createdUser).when(userGroupManager).saveUserWithoutGroup(userId);

        assertThat(userCreationService.createNewUser(userRequest), is(createdUser));
    }
}
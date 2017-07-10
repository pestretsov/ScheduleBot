package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulebotservice.user.UserCreationService;
import org.anstreth.schedulebot.schedulebotservice.user.UserGroupManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserCreationServiceTest {

    @InjectMocks
    private UserCreationService userCreationService;

    @Mock
    private UserGroupManager userGroupManager;

    @Test
    public void serviceCreatesUserAndAsksForGroup() {
        long userId = 1;
        UserRequest userRequest = new UserRequest(userId, "message");

        userCreationService.createNewUser(userRequest);

        verify(userGroupManager).saveUserWithoutGroup(userId);
    }
}
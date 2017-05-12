package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
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
    private MessageSender messageSender;

    @Mock
    private UserGroupManager userGroupManager;

    @Test
    public void serviceCreatesUserAndAsksForGroup() {
        long userId = 1;
        UserRequest userRequest = new UserRequest(userId, "message");
        String noGroupSpecifiedMessage = "Send me your group number like '12345/6' to get your schedule.";

        userCreationService.createUserAndAskForGroup(userRequest, messageSender);

        verify(userGroupManager).saveUserWithoutGroup(userId);
        verify(messageSender).sendMessage(noGroupSpecifiedMessage);
    }
}
package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.schedulebot.model.User;
import org.anstreth.schedulebot.model.UserState;
import org.anstreth.schedulebot.schedulebotservice.user.UserCreationService;
import org.anstreth.schedulebot.schedulerrepository.UserRepository;
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
    private UserRepository userRepository;

    @Test
    public void serviceCreatesUserAndAsksForGroup() {
        long userId = 1;
        User savedUser = mock(User.class);
        User freshUser = new User(userId, UserState.NO_GROUP);
        doReturn(savedUser).when(userRepository).save(freshUser);

        assertThat(userCreationService.createNewUser(userId), is(savedUser));
    }
}

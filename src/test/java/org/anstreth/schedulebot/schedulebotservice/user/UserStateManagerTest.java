package org.anstreth.schedulebot.schedulebotservice.user;

import org.anstreth.schedulebot.model.User;
import org.anstreth.schedulebot.model.UserState;
import org.anstreth.schedulebot.schedulerrepository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class UserStateManagerTest {

    @InjectMocks
    private UserStateManager userStateManager;

    @Mock
    private UserRepository userRepository;

    @Test
    public void transitToAskedForGroup_setsUserStateTo_ASKED_FOR_GROUP_andUpdates_repository() throws Exception {
        User user = new User(1, 2, UserState.NO_GROUP);
        User updatedUser = new User(1, 2, UserState.ASKED_FOR_GROUP);
        doReturn(updatedUser).when(userRepository).save(updatedUser);

        assertThat(userStateManager.transitToAskedForGroup(user),
            is(updatedUser));
    }

    @Test
    public void transitToAskedForGroup_getsUserFrom_repository_setsUserStateTo_ASKED_FOR_GROUP_andUpdates_repository() throws Exception {
        long userId = 1;
        User user = new User(userId, 2, UserState.NO_GROUP);
        User updatedUser = new User(userId, 2, UserState.ASKED_FOR_GROUP);
        doReturn(user).when(userRepository).getUserById(userId);
        doReturn(updatedUser).when(userRepository).save(updatedUser);

        assertThat(userStateManager.transitToAskedForGroup(userId),
            is(updatedUser));
    }

    @Test
    public void transitToWithGroup_setsUserStateTo_WITH_GROUP_andUpdates_Repository() throws Exception {
        User user = new User(1, 2, UserState.NO_GROUP);
        User updatedUser = new User(1, 2, UserState.WITH_GROUP);
        doReturn(updatedUser).when(userRepository).save(updatedUser);

        assertThat(userStateManager.transitToWithGroup(user),
            is(updatedUser));
    }

    @Test
    public void transitToWithGroup_getsUserFrom_repository_setsUserStateTo_WITH_GROUP_andUpdates_Repository() throws Exception {
        long userId = 1;
        User user = new User(userId, 2, UserState.NO_GROUP);
        User updatedUser = new User(userId, 2, UserState.WITH_GROUP);
        doReturn(user).when(userRepository).getUserById(userId);
        doReturn(updatedUser).when(userRepository).save(updatedUser);

        assertThat(userStateManager.transitToWithGroup(userId),
            is(updatedUser));
    }

    @Test
    public void transitToMen_getsUserFrom_repository_setsUserStateTo_MENU_andSavesHim() {
        long userId = 1;
        User user = new User(userId, 2, UserState.WITH_GROUP);
        User updatedUser = new User(userId, 2, UserState.MENU);
        doReturn(user).when(userRepository).getUserById(userId);
        doReturn(updatedUser).when(userRepository).save(updatedUser);

        assertThat(userStateManager.transitToMenu(userId), is(updatedUser));
    }
}

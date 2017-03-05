package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.ruzapi.response.Group;
import org.anstreth.ruzapi.response.Groups;
import org.anstreth.ruzapi.ruzapirepository.GroupsRepository;
import org.anstreth.schedulebot.model.User;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulerrepository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserManagerTest {

    @InjectMocks
    private UserManager userManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessageSender messageSender;

    @Mock
    private GroupsRepository groupRepository;

    @Test
    public void ifUserRepositoryReturnsNullGetGroupOfUserReturnsEmptyOptional() throws Exception {
        long userId = 1L;
        when(userRepository.getUserById(userId)).thenReturn(null);

        assertThat(userManager.getGroupIdOfUser(userId), is(Optional.empty()));
    }

    @Test
    public void ifUserRepositoryReturnsUserWithoutGroupThenGetGroupOfUserReturnsEmptyOptional() throws Exception {
        long userId = 1L;
        when(userRepository.getUserById(userId)).thenReturn(new User(userId, User.NO_GROUP_SPECIFIED));

        assertThat(userManager.getGroupIdOfUser(userId), is(Optional.empty()));
    }

    @Test
    public void ifUserRepositoryReturnsUserWithGroupItsIdIsReturned() throws Exception {
        long userId = 1L;
        int groupId = 2;
        when(userRepository.getUserById(userId)).thenReturn(new User(userId, groupId));

        assertThat(userManager.getGroupIdOfUser(userId), is(Optional.of(groupId)));

    }

    @Test
    public void ifUserRepositoryReturnsNullNewUserWithoutGroupIsCreatedAndUserIsAskedToSendHisGroup() throws Exception {
        long userId = 1L;
        String noGroupSpecifiedMessage = "Send me your group number like '12345/6' to get your schedule.";
        when(userRepository.getUserById(userId)).thenReturn(null);

        userManager.handleUserAbsense(new UserRequest(userId, "message"), messageSender);

        verify(userRepository).save(new User(userId, User.NO_GROUP_SPECIFIED));
        verify(messageSender).sendMessage(noGroupSpecifiedMessage);
    }

    @Test
    public void ifUserGroupIsNotSpecifiedAndGroupRepositoryReturnsSomeGroupByQueryThenItsGroupIsSetAsUserGroupAndUserIsUpdated() throws Exception {
        long userId = 1L;
        int foundGroupId = 2;
        String requestMessage = "group name";
        String foundGroupName = "found group name";
        Group group = getGroupWithIdAndName(foundGroupId, foundGroupName);
        Groups groups = createGroupsWithGroupInside(group);
        User userWithoutGroup = new User(userId, User.NO_GROUP_SPECIFIED);
        when(userRepository.getUserById(userId)).thenReturn(userWithoutGroup);
        when(groupRepository.findGroupsByName(requestMessage)).thenReturn(groups);

        userManager.handleUserAbsense(new UserRequest(userId, requestMessage), messageSender);

        verify(userRepository).save(new User(userId, foundGroupId));
        verify(messageSender).sendMessage("Your group is set to 'found group name'.");
    }

    private Groups createGroupsWithGroupInside(Group group) {
        Groups groups = new Groups();
        groups.setGroups(Collections.singletonList(group));
        return groups;
    }

    private Group getGroupWithIdAndName(int foundGroupId, String foundGroupName) {
        Group group = new Group();
        group.setName(foundGroupName);
        group.setId(foundGroupId);
        return group;
    }
}
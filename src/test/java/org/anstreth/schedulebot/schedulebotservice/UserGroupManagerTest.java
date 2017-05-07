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
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserGroupManagerTest {

    @InjectMocks
    private UserGroupManager userGroupManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessageSender messageSender;

    @Mock
    private GroupsRepository groupRepository;

    private final long userId = 1L;
    private final String requestMessage = "request message";
    private final UserRequest userRequest = new UserRequest(userId, requestMessage);
    private final String noGroupFoundMessage = "No group by name 'request message' is found! Try again.";
    private final User userWithoutGroup = new User(userId, User.NO_GROUP_SPECIFIED);

    @Test
    public void ifUserRepositoryReturnsNullGetGroupOfUserReturnsEmptyOptional() throws Exception {
        when(userRepository.getUserById(userId)).thenReturn(null);

        assertThat(userGroupManager.getGroupIdOfUser(userId), is(Optional.empty()));
    }

    @Test
    public void ifUserRepositoryReturnsUserWithoutGroupThenGetGroupOfUserReturnsEmptyOptional() throws Exception {
        when(userRepository.getUserById(userId)).thenReturn(userWithoutGroup);

        assertThat(userGroupManager.getGroupIdOfUser(userId), is(Optional.empty()));
    }

    @Test
    public void ifUserRepositoryReturnsUserWithGroupItsIdIsReturned() throws Exception {
        int groupId = 2;
        when(userRepository.getUserById(userId)).thenReturn(new User(userId, groupId));

        assertThat(userGroupManager.getGroupIdOfUser(userId), is(Optional.of(groupId)));
    }

    @Test
    public void ifUserRepositoryReturnsNullNewUserWithoutGroupIsCreatedAndUserIsAskedToSendHisGroup() throws Exception {
        String noGroupSpecifiedMessage = "Send me your group number like '12345/6' to get your schedule.";
        when(userRepository.getUserById(userId)).thenReturn(null);

        userGroupManager.handleUserAbsense(userRequest, messageSender);

        verify(userRepository).save(userWithoutGroup);
        verify(messageSender).sendMessage(noGroupSpecifiedMessage);
    }

    @Test
    public void ifUserGroupIsNotSpecifiedAndGroupRepositoryReturnsSomeGroupByQueryThenItsGroupIsSetAsUserGroupAndUserIsUpdated() throws Exception {
        int foundGroupId = 2;
        String foundGroupName = "found group name";
        Group group = getGroupWithIdAndName(foundGroupId, foundGroupName);
        Groups groups = createGroupsWithGroupInside(group);
        User userWithoutGroup = this.userWithoutGroup;
        when(userRepository.getUserById(userId)).thenReturn(userWithoutGroup);
        when(groupRepository.findGroupsByName(requestMessage)).thenReturn(groups);

        userGroupManager.handleUserAbsense(userRequest, messageSender);

        verify(userRepository).save(new User(userId, foundGroupId));
        verify(messageSender).sendMessage("Your group is set to 'found group name'.");
    }

    @Test
    public void ifUserGroupIsNotSpecifiedAndGroupRepositoryReturnsGroupsWithNullThenUserIsAskedAgain() throws Exception {
        Groups groupsWithNull = new Groups();
        when(userRepository.getUserById(userId)).thenReturn(userWithoutGroup);
        when(groupRepository.findGroupsByName(requestMessage)).thenReturn(groupsWithNull);

        userGroupManager.handleUserAbsense(userRequest, messageSender);

        verify(messageSender).sendMessage(noGroupFoundMessage);
    }

    @Test
    public void ifUserGroupIsNotSpecifiedAndGroupRepositoryReturnsGroupsWithNoGroupsThenUserIsAskedAgain() throws Exception {
        Groups groupsWithZeroGroups = new Groups();
        groupsWithZeroGroups.setGroups(Collections.emptyList());
        when(userRepository.getUserById(userId)).thenReturn(userWithoutGroup);
        when(groupRepository.findGroupsByName(requestMessage)).thenReturn(groupsWithZeroGroups);

        userGroupManager.handleUserAbsense(userRequest, messageSender);

        verify(messageSender).sendMessage(noGroupFoundMessage);
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
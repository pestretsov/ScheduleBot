package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.ruzapi.response.Group;
import org.anstreth.ruzapi.response.Groups;
import org.anstreth.ruzapi.ruzapirepository.GroupsRepository;
import org.anstreth.schedulebot.exceptions.NoGroupForUserException;
import org.anstreth.schedulebot.exceptions.NoSuchGroupFoundException;
import org.anstreth.schedulebot.exceptions.NoSuchUserException;
import org.anstreth.schedulebot.model.User;
import org.anstreth.schedulebot.schedulerrepository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.anstreth.schedulebot.model.UserState.NO_GROUP;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class UserGroupManagerTest {

    @InjectMocks
    private UserGroupManager userGroupManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GroupsRepository groupRepository;

    private final long userId = 1L;

    @Test(expected = NoSuchUserException.class)
    public void ifThereAreNoSuchUserInRepoThen_NoSuchUserException_isThrown() throws Exception {
        doReturn(null).when(userRepository).getUserById(userId);

        userGroupManager.getGroupIdOfUser(userId);
    }

    @Test(expected = NoGroupForUserException.class)
    public void ifUser_groupId_isNotSetThen_NoGroupForUser__isThrown() throws Exception {
        User userWithNoGroup = new User(userId, User.NO_GROUP_SPECIFIED);
        doReturn(userWithNoGroup).when(userRepository).getUserById(userId);

        userGroupManager.getGroupIdOfUser(userId);
    }

    @Test
    public void ifUserIsInRepoAndGroupIdIsOk_ThenGroupIdIsReturned() throws Exception {
        int groupId = 2;
        User userWithNoGroup = new User(userId, groupId);
        doReturn(userWithNoGroup).when(userRepository).getUserById(userId);

        int receivedId = userGroupManager.getGroupIdOfUser(userId);

        assertThat(receivedId, is(groupId));
    }

    @Test
    public void createUserWithNoGroup_createsNewUserInRepository() {
        userGroupManager.saveUserWithoutGroup(userId);

        then(userRepository).should().save(new User(userId, User.NO_GROUP_SPECIFIED, NO_GROUP));
    }

    @Test
    public void findAndSetGroupForUser_setsGroupIdIfFindsGroupIn_groupsRepository_andSavesUser() {
        int groupId = 2;
        String groupName = "group name";
        Group group = getGroupWithIdAndName(groupId, groupName);
        Groups groups = createGroupsWithGroupInside(group);
        doReturn(groups).when(groupRepository).findGroupsByName(groupName);
        doReturn(new User(userId, User.NO_GROUP_SPECIFIED)).when(userRepository).getUserById(userId);

        userGroupManager.findAndSetGroupForUser(userId, groupName);

        then(userRepository).should().save(new User(userId, groupId));
    }

    @Test(expected = NoSuchGroupFoundException.class)
    public void if_groupsRepository_returnsGroupsWithNullThen_NoGroupFoundException_isThrown() {
        String groupName = "group name";
        doReturn(new Groups()).when(groupRepository).findGroupsByName(groupName);

        userGroupManager.findAndSetGroupForUser(userId, groupName);
    }

    @Test(expected = NoSuchGroupFoundException.class)
    public void if_groupsRepository_returnsGroupsWithNoGroupsThen_NoGroupFoundException_isThrown() {
        String groupName = "group name";
        Groups groups = new Groups();
        groups.setGroups(Collections.emptyList());
        doReturn(groups).when(groupRepository).findGroupsByName(groupName);

        userGroupManager.findAndSetGroupForUser(userId, groupName);
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

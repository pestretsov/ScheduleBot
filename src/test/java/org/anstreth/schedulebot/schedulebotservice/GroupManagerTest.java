package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.ruzapi.response.Group;
import org.anstreth.ruzapi.response.Groups;
import org.anstreth.ruzapi.ruzapirepository.GroupsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class GroupManagerTest {

    @InjectMocks
    private GroupManager groupManager;

    @Mock
    private GroupsRepository groupReposiory;

    @Test
    public void when_groupRepository_returns_null_emptyIsReturned() throws Exception {
        String groupName = "name";
        doReturn(null).when(groupReposiory).findGroupsByName(groupName);

        assertThat(groupManager.findGroupByName(groupName), is(Optional.empty()));
    }

    @Test
    public void when_groupRepository_returns_Groups_withNullList_thenEmptyIsReturned() throws Exception {
        String groupName = "name";
        doReturn(new Groups()).when(groupReposiory).findGroupsByName(groupName);

        assertThat(groupManager.findGroupByName(groupName), is(Optional.empty()));
    }

    @Test
    public void when_groupRepository_returns_Groups_withEmptyList_thenEmptyIsReturned() throws Exception {
        String groupName = "name";
        Groups groups = new Groups();
        groups.setGroups(Collections.emptyList());
        doReturn(groups).when(groupReposiory).findGroupsByName(groupName);

        assertThat(groupManager.findGroupByName(groupName), is(Optional.empty()));
    }

    @Test
    public void when_groupRepository_returns_Groups_withAtLeastOneGroupInside_ItIsReturned() throws Exception {
        String groupName = "name";
        Groups groups = new Groups();
        Group group = mock(Group.class);
        groups.setGroups(Collections.singletonList(group));
        doReturn(groups).when(groupReposiory).findGroupsByName(groupName);

        assertThat(groupManager.findGroupByName(groupName), is(Optional.of(group)));
    }
}
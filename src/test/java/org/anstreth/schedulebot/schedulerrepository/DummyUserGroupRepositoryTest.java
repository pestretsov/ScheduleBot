package org.anstreth.schedulebot.schedulerrepository;

import org.junit.Test;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DummyUserGroupRepositoryTest {
    private DummyUserGroupRepository repository = new DummyUserGroupRepository();

    @Test
    public void repositoryReturnsNullIfHasNoInformation() throws Exception {
        assertThat(repository.get(1), is(nullValue()));
    }

    @Test
    public void ifValueIsSavedRepositoryReturnsIt() throws Exception {
        long userId = 1;
        int groupId = 2;

        repository.save(userId, groupId);

        assertThat(repository.get(userId), is(groupId));
    }

    @Test
    public void ifRepositoryIsEmptyRemoveDoesNothing() throws Exception {
        long nonExistingUserId = 1;
        repository.remove(nonExistingUserId);
    }

    @Test
    public void ifValueIsRemovedThenRepositoryReturnsNull() throws Exception {
        long userId = 1;
        repository.save(userId, 2);
        repository.remove(userId);

        assertThat(repository.get(userId), is(nullValue()));
    }
}
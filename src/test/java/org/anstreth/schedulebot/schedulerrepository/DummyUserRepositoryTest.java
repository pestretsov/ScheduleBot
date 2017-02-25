package org.anstreth.schedulebot.schedulerrepository;

import org.anstreth.schedulebot.model.User;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class DummyUserRepositoryTest {
    private DummyUserRepository dummyUserRepository = new DummyUserRepository();

    @Test
    public void ifThereAreNoSuchUserNullIsReturned() throws Exception {
        assertThat(dummyUserRepository.getUserById(1), nullValue());
    }

    @Test
    public void afterSaveUserAppearsInDummyRepository() throws Exception {
        User savedUser = dummyUserRepository.save(new User(1, 2));

        assertThat(savedUser, notNullValue());
        assertThat(dummyUserRepository.getUserById(1), is(savedUser));
    }

    @Test
    public void afterDeletionUserDissapears() throws Exception {
        User savedUser = dummyUserRepository.save(new User(1, 2));

        dummyUserRepository.delete(savedUser);

        assertThat(dummyUserRepository.getUserById(1), nullValue());
    }

    @Test
    public void youCanUpdateUserWithSave() throws Exception {
        User savedUser = dummyUserRepository.save(new User(1, 2));
        int updatedGroupId = 3;
        User updatedUser = new User(savedUser.getId(), updatedGroupId);

        dummyUserRepository.save(updatedUser);

        assertThat(dummyUserRepository.getUserById(1).getGroupId(), is(updatedGroupId));
    }
}
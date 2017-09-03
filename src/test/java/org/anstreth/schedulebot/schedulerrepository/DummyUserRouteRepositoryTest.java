package org.anstreth.schedulebot.schedulerrepository;

import org.anstreth.schedulebot.model.UserRoute;
import org.junit.Test;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DummyUserRouteRepositoryTest {
    private DummyUserRouteRepository repository = new DummyUserRouteRepository();

    @Test
    public void null_is_returned_if_no_route_was_saved() throws Exception {
        assertThat(repository.get(1), is(nullValue()));
    }

    @Test
    public void saved_value_is_returned() throws Exception {
        long userId = 1;
        UserRoute route = UserRoute.HOME;

        repository.save(userId, route);

        assertThat(repository.get(userId), is(route));
    }

    @Test
    public void empty_repository_does_nothing_on_remove() throws Exception {
        long nonExistingUserId = 1;
        repository.remove(nonExistingUserId);
    }

    @Test
    public void if_value_is_removed_then_null_is_returned() throws Exception {
        long userId = 1;
        repository.save(userId, UserRoute.HOME);
        repository.remove(userId);

        assertThat(repository.get(userId), is(nullValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void when_route_is_null_exception_is_thrown() throws Exception {
        repository.save(1L, null);
    }
}

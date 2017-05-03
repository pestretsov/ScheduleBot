package org.anstreth.schedulebot.schedulerbotcommandshandler.handlers;

import org.anstreth.ruzapi.response.WeekSchedule;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.WeekResponse;
import org.anstreth.schedulebot.schedulerrepository.SchedulerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class WeekScheduleRequestHandlerTest {

    @Mock
    private SchedulerRepository repository;

    @Test
    public void handlerShouldTakeWeekFromRepoAndReturnWeekResponse() {
        int groupId = 1;
        String message = "message";
        WeekSchedule weekFromRepo = mock(WeekSchedule.class);
        given(repository.getScheduleForGroupForWeek(eq(groupId), any())).willReturn(weekFromRepo);
        WeekScheduleRequestHandler handler = new WeekScheduleRequestHandler(repository);

        WeekResponse response = handler.handle(new ScheduleRequest(groupId, message));

        assertThat(response.getWeekSchedule(), is(weekFromRepo));
    }
}
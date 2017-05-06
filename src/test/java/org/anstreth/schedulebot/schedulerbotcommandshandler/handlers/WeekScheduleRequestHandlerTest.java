package org.anstreth.schedulebot.schedulerbotcommandshandler.handlers;

import org.anstreth.ruzapi.response.Day;
import org.anstreth.ruzapi.response.WeekSchedule;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.NoScheduleForWeekResponse;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.ScheduleResponse;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.WeekResponse;
import org.anstreth.schedulebot.schedulerrepository.SchedulerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class WeekScheduleRequestHandlerTest {

    @Mock
    private SchedulerRepository repository;

    @Test
    public void handlerShouldTakeWeekFromRepoAndReturnWeekResponse() {
        int groupId = 1;
        String message = "message";
        WeekSchedule weekFromRepo = new WeekSchedule();
        weekFromRepo.setDays(Collections.singletonList(new Day()));
        given(repository.getScheduleForGroupForWeek(eq(groupId), any())).willReturn(weekFromRepo);
        WeekScheduleRequestHandler handler = new WeekScheduleRequestHandler(repository);

        WeekResponse response = (WeekResponse) handler.handle(new ScheduleRequest(groupId, message));

        assertThat(response.getWeekSchedule(), is(weekFromRepo));
    }

    @Test
    public void ifWeekDaysAreNullThen_noScheduleForWeek_isReturned() {
        int groupId = 1;
        String message = "message";
        WeekSchedule weekFromRepo = new WeekSchedule();
        weekFromRepo.setDays(null);
        given(repository.getScheduleForGroupForWeek(eq(groupId), any())).willReturn(weekFromRepo);
        WeekScheduleRequestHandler handler = new WeekScheduleRequestHandler(repository);

        ScheduleResponse response = handler.handle(new ScheduleRequest(groupId, message));

        assertThat(response, is(instanceOf(NoScheduleForWeekResponse.class)));
    }

    @Test
    public void ifWeekDaysAreEmptyThen_noSheduleForWeek_isReturned() {
        int groupId = 1;
        String message = "message";
        WeekSchedule weekFromRepo = new WeekSchedule();
        weekFromRepo.setDays(Collections.emptyList());
        given(repository.getScheduleForGroupForWeek(eq(groupId), any())).willReturn(weekFromRepo);
        WeekScheduleRequestHandler handler = new WeekScheduleRequestHandler(repository);

        ScheduleResponse response = handler.handle(new ScheduleRequest(groupId, message));

        assertThat(response, is(instanceOf(NoScheduleForWeekResponse.class)));
    }
}
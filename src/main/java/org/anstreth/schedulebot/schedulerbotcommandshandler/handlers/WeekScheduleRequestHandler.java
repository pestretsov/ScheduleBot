package org.anstreth.schedulebot.schedulerbotcommandshandler.handlers;

import org.anstreth.ruzapi.response.WeekSchedule;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.WeekResponse;
import org.anstreth.schedulebot.schedulerrepository.SchedulerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.TimeZone;

@Component
public class WeekScheduleRequestHandler implements SchedulerRequestHandler {

    private final SchedulerRepository repository;

    @Autowired
    public WeekScheduleRequestHandler(SchedulerRepository repository) {
        this.repository = repository;
    }

    @Override
    public WeekResponse handle(ScheduleRequest request) {
        Calendar now = Calendar.getInstance();
        WeekSchedule week = repository.getScheduleForGroupForWeek(request.getGroupId(), now);
        return new WeekResponse(week);
    }
}

package org.anstreth.schedulebot.schedulerbotcommandshandler.handlers;

import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.ScheduleResponse;
import org.anstreth.schedulebot.schedulerrepository.SchedulerRepository;

import java.util.Calendar;

public class TodayScheduleRequestHandler extends OneDayScheduleRequestHandler {
    TodayScheduleRequestHandler(SchedulerRepository schedulerRepository) {
        super(schedulerRepository);
    }

    public ScheduleResponse handle(ScheduleRequest request) {
        return getScheduleResponseForGroupForDate(request.getGroupId(), Calendar.getInstance());
    }
}

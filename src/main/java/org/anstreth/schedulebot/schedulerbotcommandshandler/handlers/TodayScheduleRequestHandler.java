package org.anstreth.schedulebot.schedulerbotcommandshandler.handlers;

import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.anstreth.schedulebot.schedulerrepository.SchedulerRepository;

import java.util.Calendar;

public class TodayScheduleRequestHandler extends AbstractScheduleRequestHandler {
    TodayScheduleRequestHandler(SchedulerFormatter schedulerFormatter, SchedulerRepository schedulerRepository) {
        super(schedulerFormatter, schedulerRepository);
    }

    public String handle(ScheduleRequest request) {
        return getScheduleForGroupForDate(request.getGroupId(), Calendar.getInstance());
    }
}

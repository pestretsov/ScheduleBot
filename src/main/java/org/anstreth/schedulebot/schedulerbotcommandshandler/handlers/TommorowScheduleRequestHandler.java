package org.anstreth.schedulebot.schedulerbotcommandshandler.handlers;

import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.anstreth.schedulebot.schedulerrepository.SchedulerRepository;

import java.util.Calendar;

public class TommorowScheduleRequestHandler extends AbstractScheduleRequestHandler {
    TommorowScheduleRequestHandler(SchedulerFormatter schedulerFormatter, SchedulerRepository schedulerRepository) {
        super(schedulerFormatter, schedulerRepository);
    }

    public String handle(ScheduleRequest request) {
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DATE, 1);
        return getScheduleForGroupForDate(request.getGroupId(), today);
    }
}

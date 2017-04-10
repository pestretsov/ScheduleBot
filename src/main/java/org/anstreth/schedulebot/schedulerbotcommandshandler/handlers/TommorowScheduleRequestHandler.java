package org.anstreth.schedulebot.schedulerbotcommandshandler.handlers;

import org.anstreth.schedulebot.schedulebotservice.MessageSender;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.anstreth.schedulebot.schedulerrepository.SchedulerRepository;

import java.util.Calendar;

public class TommorowScheduleRequestHandler extends AbstractScheduleRequestHandler {
    TommorowScheduleRequestHandler(SchedulerFormatter schedulerFormatter, SchedulerRepository schedulerRepository) {
        super(schedulerFormatter, schedulerRepository);
    }

    public void handle(ScheduleRequest request, MessageSender sender) {
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DATE, 1);
        sender.sendMessage(getScheduleForGroupForDate(request.getGroupId(), today));
    }
}

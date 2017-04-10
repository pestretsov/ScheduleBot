package org.anstreth.schedulebot.schedulerbotcommandshandler.handlers;

import org.anstreth.schedulebot.schedulebotservice.MessageSender;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.anstreth.schedulebot.schedulerrepository.SchedulerRepository;

import java.util.Calendar;

public class TodayScheduleRequestHandler extends AbstractScheduleRequestHandler {
    TodayScheduleRequestHandler(SchedulerFormatter schedulerFormatter, SchedulerRepository schedulerRepository) {
        super(schedulerFormatter, schedulerRepository);
    }

    public void handle(ScheduleRequest request, MessageSender sender) {
        sender.sendMessage(getScheduleForGroupForDate(request.getGroupId(), Calendar.getInstance()));
    }
}

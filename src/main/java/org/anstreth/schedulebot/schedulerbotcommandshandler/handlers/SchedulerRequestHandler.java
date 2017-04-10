package org.anstreth.schedulebot.schedulerbotcommandshandler.handlers;

import org.anstreth.schedulebot.schedulebotservice.MessageSender;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;

public interface SchedulerRequestHandler {
    void handle(ScheduleRequest request, MessageSender sender);
}

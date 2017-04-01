package org.anstreth.schedulebot.schedulerbotcommandshandler.handlers;

import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;

public interface SchedulerRequestHandler {
    String handle(ScheduleRequest request);
}

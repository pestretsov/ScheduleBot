package org.anstreth.schedulebot.schedulerbotcommandshandler.handlers;

import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;

public class UnrecognizedCommandHandler implements SchedulerRequestHandler {

    @Override
    public String handle(ScheduleRequest request) {
        return "Sorry, don't understand that!";
    }
}

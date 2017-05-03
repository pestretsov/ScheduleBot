package org.anstreth.schedulebot.schedulerbotcommandshandler.handlers;

import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.ScheduleResponse;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.SimpleStringResponse;

public class UnrecognizedCommandHandler implements SchedulerRequestHandler {

    @Override
    public ScheduleResponse handle(ScheduleRequest request) {
        return new SimpleStringResponse("Sorry, don't understand that!");
    }
}

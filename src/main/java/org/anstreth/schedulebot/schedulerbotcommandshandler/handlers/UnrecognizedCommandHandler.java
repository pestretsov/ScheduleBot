package org.anstreth.schedulebot.schedulerbotcommandshandler.handlers;

import org.anstreth.schedulebot.schedulebotservice.MessageSender;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;

public class UnrecognizedCommandHandler implements SchedulerRequestHandler {

    @Override
    public void handle(ScheduleRequest request, MessageSender sender) {
        sender.sendMessage("Sorry, don't understand that!");
    }
}

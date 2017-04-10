package org.anstreth.schedulebot.schedulerbotcommandshandler;

import lombok.extern.log4j.Log4j;
import org.anstreth.schedulebot.schedulerbotcommandshandler.handlers.*;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulebotservice.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j
public class SchedulerBotCommandsHandler {
    private final ScheduleRequestHandlersSupplier scheduleRequestHandlersSupplier;

    @Autowired
    SchedulerBotCommandsHandler(ScheduleRequestHandlersSupplier scheduleRequestHandlersSupplier) {
        this.scheduleRequestHandlersSupplier = scheduleRequestHandlersSupplier;
    }

    public void handleRequest(ScheduleRequest userRequest, MessageSender messageSender) {
        log.info("Handling request: " + userRequest.getMessage());
        SchedulerRequestHandler requestHandler = scheduleRequestHandlersSupplier.getHandlerForCommand(userRequest.getMessage());
        requestHandler.handle(userRequest, messageSender);
    }

}

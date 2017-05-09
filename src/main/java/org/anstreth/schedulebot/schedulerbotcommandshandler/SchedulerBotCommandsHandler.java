package org.anstreth.schedulebot.schedulerbotcommandshandler;

import lombok.extern.log4j.Log4j;
import org.anstreth.schedulebot.schedulebotservice.MessageWithRepliesSender;
import org.anstreth.schedulebot.schedulerbotcommandshandler.handlers.ScheduleRequestHandlersRouter;
import org.anstreth.schedulebot.schedulerbotcommandshandler.handlers.SchedulerRequestHandler;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.ScheduleResponse;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Log4j
public class SchedulerBotCommandsHandler {
    private final SchedulerFormatter schedulerFormatter;
    private final ScheduleRequestHandlersRouter scheduleRequestHandlersRouter;
    private final List<String> possibleReplies = Arrays.asList("/today", "/tomorrow", "/week");

    @Autowired
    SchedulerBotCommandsHandler(ScheduleRequestHandlersRouter scheduleRequestHandlersRouter, SchedulerFormatter schedulerFormatter) {
        this.scheduleRequestHandlersRouter = scheduleRequestHandlersRouter;
        this.schedulerFormatter = schedulerFormatter;
    }

    public void handleRequest(ScheduleRequest userRequest, MessageWithRepliesSender messageSender) {
        log.info("Handling command: " + userRequest.getCommand());
        SchedulerRequestHandler requestHandler = scheduleRequestHandlersRouter.getHandlerForCommand(userRequest.getCommand());
        ScheduleResponse response = requestHandler.handle(userRequest);
        response.formatAndSend(schedulerFormatter, messageSender.withReplies(possibleReplies));
    }

}

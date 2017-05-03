package org.anstreth.schedulebot.schedulerbotcommandshandler.handlers;

import org.anstreth.schedulebot.schedulerrepository.SchedulerRepository;
import org.springframework.stereotype.Component;

@Component
public class ScheduleRequestHandlersRouter {
    private final SchedulerRepository schedulerRepository;

    ScheduleRequestHandlersRouter(SchedulerRepository schedulerRepository) {
        this.schedulerRepository = schedulerRepository;
    }

    public SchedulerRequestHandler getHandlerForCommand(String command) {
        String trimmedCommand = command.trim();
        return getScheduleRequestHandler(trimmedCommand);
    }

    private SchedulerRequestHandler getScheduleRequestHandler(String trimmedCommand) {
        switch (trimmedCommand) {
            case "/today":
                return new TodayScheduleRequestHandler(schedulerRepository);
            case "/tomorrow":
                return new TommorowScheduleRequestHandler(schedulerRepository);
            default:
                return new UnrecognizedCommandHandler();
        }
    }
}

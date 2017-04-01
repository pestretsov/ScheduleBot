package org.anstreth.schedulebot.schedulerbotcommandshandler.handlers;

import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.anstreth.schedulebot.schedulerrepository.SchedulerRepository;
import org.springframework.stereotype.Component;

@Component
public class ScheduleRequestHandlersSupplier {
    private final SchedulerFormatter schedulerFormatter;
    private final SchedulerRepository schedulerRepository;

    ScheduleRequestHandlersSupplier(SchedulerRepository schedulerRepository, SchedulerFormatter schedulerFormatter) {
        this.schedulerFormatter = schedulerFormatter;
        this.schedulerRepository = schedulerRepository;
    }

    public SchedulerRequestHandler getHandlerForCommand(String command) {
        String trimmedCommand = command.trim();
        return getScheduleRequestHandler(trimmedCommand);
    }

    private SchedulerRequestHandler getScheduleRequestHandler(String trimmedCommand) {
        switch (trimmedCommand) {
            case "/today":
                return new TodayScheduleRequestHandler(schedulerFormatter, schedulerRepository);
            case "/tomorrow":
                return new TommorowScheduleRequestHandler(schedulerFormatter, schedulerRepository);
            default:
                return new UnrecognizedCommandHandler();
        }
    }
}

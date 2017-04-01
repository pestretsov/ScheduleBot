package org.anstreth.schedulebot.schedulerbotcommandshandler.handlers;

import lombok.extern.log4j.Log4j;
import org.anstreth.schedulebot.exceptions.NoScheduleForDay;
import org.anstreth.schedulebot.schedulerbotcommandshandler.SchedulerBotCommandsHandler;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.anstreth.schedulebot.schedulerrepository.SchedulerRepository;

import java.util.Calendar;

@Log4j
abstract class AbstractScheduleRequestHandler implements SchedulerRequestHandler {
    private SchedulerFormatter schedulerFormatter;
    private SchedulerRepository schedulerRepository;

    AbstractScheduleRequestHandler(SchedulerFormatter schedulerFormatter, SchedulerRepository schedulerRepository) {
        this.schedulerFormatter = schedulerFormatter;
        this.schedulerRepository = schedulerRepository;
    }

    protected String getScheduleForGroupForDate(int groupId, Calendar date) {
        try {
            return schedulerFormatter.formatDay(schedulerRepository.getScheduleForGroupForDay(groupId, date));
        } catch (NoScheduleForDay e) {
            return schedulerFormatter.getNoScheduleForDateMessage(date);
        } catch (Exception e) {
            log.info("Some error occurred!", e);
            return "Some error occurred!";
        }
    }
}

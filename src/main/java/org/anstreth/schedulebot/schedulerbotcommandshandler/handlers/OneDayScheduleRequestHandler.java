package org.anstreth.schedulebot.schedulerbotcommandshandler.handlers;

import lombok.extern.log4j.Log4j;
import org.anstreth.ruzapi.response.Day;
import org.anstreth.schedulebot.exceptions.NoScheduleForDay;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.DayResponse;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.NoScheduleResponse;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.ScheduleResponse;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.SimpleStringResponse;
import org.anstreth.schedulebot.schedulerrepository.SchedulerRepository;

import java.util.Calendar;

@Log4j
abstract class OneDayScheduleRequestHandler implements SchedulerRequestHandler {
    private SchedulerRepository schedulerRepository;

    OneDayScheduleRequestHandler(SchedulerRepository schedulerRepository) {
        this.schedulerRepository = schedulerRepository;
    }

    protected ScheduleResponse getScheduleResponseForGroupForDate(int groupId, Calendar date) {
        try {
            Day scheduleForGroupForDay = schedulerRepository.getScheduleForGroupForDay(groupId, date);
            return new DayResponse(scheduleForGroupForDay);
        } catch (NoScheduleForDay e) {
            return new NoScheduleResponse(date);
        } catch (Exception e) {
            log.info("Some error occurred!", e);
            return new SimpleStringResponse("Some error occurred!");
        }
    }
}

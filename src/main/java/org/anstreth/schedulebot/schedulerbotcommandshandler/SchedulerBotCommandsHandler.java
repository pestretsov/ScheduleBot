package org.anstreth.schedulebot.schedulerbotcommandshandler;

import lombok.extern.log4j.Log4j;
import org.anstreth.schedulebot.exceptions.NoScheduleForDay;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.anstreth.schedulebot.schedulerrepository.SchedulerRepository;
import org.anstreth.schedulebot.scheduleuserservice.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
@Log4j
public class SchedulerBotCommandsHandler {
    private final SchedulerRepository schedulerRepository;
    private final SchedulerFormatter schedulerFormatter;

    @Autowired
    SchedulerBotCommandsHandler(SchedulerRepository schedulerRepository, SchedulerFormatter schedulerFormatter) {
        this.schedulerRepository = schedulerRepository;
        this.schedulerFormatter = schedulerFormatter;
    }

    public void handleRequest(ScheduleRequest userRequest, MessageSender messageSender) {
        log.info("Handling request: " + userRequest.getMessage());
        messageSender.sendMessage(getReplyForRequest(userRequest));
    }

    private String getReplyForRequest(ScheduleRequest request) {
        String trimmedCommand = request.getMessage().trim();
        switch (trimmedCommand) {
            case "/today":
                return getScheduleForGroupForToday(request.getGroupId());
            case "/tomorrow":
                return getScheduleForGroupForTomorrow(request.getGroupId());
            default:
                return "Sorry, don't understand that!";
        }
    }

    private String getScheduleForGroupForToday(int groupId) {
        return getScheduleForGroupForDate(groupId, Calendar.getInstance());
    }

    private String getScheduleForGroupForTomorrow(int groupId) {
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DATE, 1);
        return getScheduleForGroupForDate(groupId, today);
    }

    private String getScheduleForGroupForDate(int groupId, Calendar date) {
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

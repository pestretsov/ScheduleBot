package org.anstreth.schedulebot.schedulebottextservice;

import lombok.extern.log4j.Log4j;
import org.anstreth.schedulebot.exceptions.NoScheduleForDay;
import org.anstreth.schedulebot.schedulebottextservice.request.UserRequest;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.anstreth.schedulebot.schedulerrepository.SchedulerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
@Log4j
public class SchedulerBotTextService {
    private final SchedulerRepository schedulerRepository;
    private final SchedulerFormatter schedulerFormatter;

    @Autowired
    SchedulerBotTextService(SchedulerRepository schedulerRepository, SchedulerFormatter schedulerFormatter) {
        this.schedulerRepository = schedulerRepository;
        this.schedulerFormatter = schedulerFormatter;
    }

    public void handleText(UserRequest userRequest, MessageSender messageSender) {
        log.info("Handling request: " + userRequest.getMessage());
        messageSender.sendMessage(getReplyForText(userRequest.getMessage()));
    }

    private String getReplyForText(String text) {
        String trimmedCommand = text.trim();

        switch (trimmedCommand) {
            case "/today":
                return getScheduleForToday();
            case "/tomorrow":
                return getScheduleForTomorrow();
            default:
                return "Sorry, don't understand that!";
        }
    }

    private String getScheduleForToday() {
        return getScheduleForDate(Calendar.getInstance());
    }

    private String getScheduleForTomorrow() {
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DATE, 1);
        return getScheduleForDate(today);
    }

    private String getScheduleForDate(Calendar date) {
        try {
            return schedulerFormatter.formatDay(schedulerRepository.getScheduleForDay(date));
        } catch (NoScheduleForDay e) {
            return schedulerFormatter.getNoScheduleForDateMessage(date);
        } catch (Exception e) {
            log.info("Some error occurred!", e);
            return "Some error occurred!";
        }
    }
}

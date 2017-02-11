package org.anstreth.schedulebot;

import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.anstreth.schedulebot.schedulerrepository.SchedulerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Objects;

@Component
class SchedulerBotTextService {
    private final SchedulerRepository schedulerRepository;
    private final SchedulerFormatter schedulerFormatter;

    @Autowired
    SchedulerBotTextService(SchedulerRepository schedulerRepository, SchedulerFormatter schedulerFormatter) {
        this.schedulerRepository = schedulerRepository;
        this.schedulerFormatter = schedulerFormatter;
    }

    String getReplyForText(String string) {
        if (isTodayCommand(string)) {
            return getScheduleForToday();
        } else {
            return "Sorry, don't understand that!";
        }

    }

    private boolean isTodayCommand(String string) {
        return Objects.equals(string, "/today");
    }

    private String getScheduleForToday() {
        try {
            return schedulerFormatter.formatDay(schedulerRepository.getScheduleForDate(Calendar.getInstance()));
        } catch (NoSuchElementException e) {
            return "Today is sunday!";
        } catch (Exception e) {
            return "Some error occured!";
        }

    }

}

package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import org.anstreth.ruzapi.response.WeekSchedule;
import org.anstreth.schedulebot.schedulebotservice.MessageSender;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;

public class WeekResponse implements ScheduleResponse {

    private final WeekSchedule weekSchedule;

    WeekResponse(WeekSchedule weekSchedule) {
        this.weekSchedule = weekSchedule;
    }

    @Override
    public void formatAndSend(SchedulerFormatter schedulerFormatter, MessageSender messageSender) {
        weekSchedule.getDays().stream()
                .map(schedulerFormatter::formatDay)
                .forEach(messageSender::sendMessage);
    }
}

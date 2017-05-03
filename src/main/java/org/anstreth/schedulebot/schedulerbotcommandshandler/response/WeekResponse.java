package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import lombok.Getter;
import org.anstreth.ruzapi.response.WeekSchedule;
import org.anstreth.schedulebot.schedulebotservice.MessageSender;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;

@Getter
public class WeekResponse implements ScheduleResponse {

    private final WeekSchedule weekSchedule;

    public WeekResponse(WeekSchedule weekSchedule) {
        this.weekSchedule = weekSchedule;
    }

    @Override
    public void formatAndSend(SchedulerFormatter schedulerFormatter, MessageSender messageSender) {
        weekSchedule.getDays().stream()
                .map(schedulerFormatter::formatDay)
                .forEach(messageSender::sendMessage);
    }
}

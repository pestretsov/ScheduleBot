package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import org.anstreth.schedulebot.schedulebotservice.MessageSender;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;

import java.util.Collections;
import java.util.List;

public class SimpleStringResponse implements ScheduleResponse {
    private final String message;

    public SimpleStringResponse(String message) {
        this.message = message;
    }

    @Override
    public void formatAndSend(SchedulerFormatter schedulerFormatter, MessageSender messageSender) {
        messageSender.sendMessage(message);
    }

    @Override
    public List<String> format(SchedulerFormatter formatter) {
        return Collections.singletonList(message);
    }
}

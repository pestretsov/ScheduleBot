package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import lombok.Getter;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;

import java.util.Collections;
import java.util.List;

public class SimpleStringResponse implements ScheduleResponse {

    @Getter
    private final String message;

    public SimpleStringResponse(String message) {
        this.message = message;
    }

    @Override
    public List<String> formatWith(SchedulerFormatter formatter) {
        return Collections.singletonList(message);
    }
}

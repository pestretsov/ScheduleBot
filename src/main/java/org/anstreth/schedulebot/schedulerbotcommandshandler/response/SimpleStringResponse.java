package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import java.util.List;
import lombok.Getter;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;

public class SimpleStringResponse implements ScheduleResponse {

    @Getter
    private final String message;

    public SimpleStringResponse(String message) {
        this.message = message;
    }

    @Override
    public List<String> formatWith(SchedulerFormatter formatter) {
        return formatter.format(this);
    }
}

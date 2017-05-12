package org.anstreth.schedulebot.commands;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ScheduleCommandParser {
    private final Map<String, ScheduleCommand> possibleNames = new HashMap<>();

    {
        possibleNames.put("/tomorrow", ScheduleCommand.TOMORROW);
        possibleNames.put("Tomorrow", ScheduleCommand.TOMORROW);

        possibleNames.put("/today", ScheduleCommand.TODAY);
        possibleNames.put("Today", ScheduleCommand.TODAY);

        possibleNames.put("/week", ScheduleCommand.WEEK);
        possibleNames.put("Week", ScheduleCommand.WEEK);
    }

    public ScheduleCommand parse(String command) {
        String trimmedCommand = command.trim();
        return possibleNames.getOrDefault(trimmedCommand, ScheduleCommand.UNKNOWN);
    }
}

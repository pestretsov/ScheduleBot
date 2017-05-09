package org.anstreth.schedulebot.commands;

import java.util.HashMap;
import java.util.Map;

class ScheduleCommandParser {
    private final Map<String, ScheduleCommand> possibleNames = new HashMap<>();

    {
        possibleNames.put("/tomorrow", ScheduleCommand.TOMORROW);
        possibleNames.put("Tomorrow", ScheduleCommand.TOMORROW);

        possibleNames.put("/today", ScheduleCommand.TODAY);
        possibleNames.put("Today", ScheduleCommand.TODAY);

        possibleNames.put("/week", ScheduleCommand.WEEK);
        possibleNames.put("Week", ScheduleCommand.WEEK);
    }

    ScheduleCommand parse(String command) {
        String trimmedCommand = command.trim();
        return possibleNames.getOrDefault(trimmedCommand, ScheduleCommand.UNKNOWN);
    }
}

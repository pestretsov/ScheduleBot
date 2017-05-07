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
        return possibleNames.getOrDefault(command, ScheduleCommand.UNKNOWN);
    }
}

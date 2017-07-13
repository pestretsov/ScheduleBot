package org.anstreth.schedulebot.commands;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserCommandParser {
    private final Map<String, UserCommand> possibleNames = new HashMap<>();

    {
        possibleNames.put("/tomorrow", UserCommand.TOMORROW);
        possibleNames.put("Tomorrow", UserCommand.TOMORROW);

        possibleNames.put("/today", UserCommand.TODAY);
        possibleNames.put("Today", UserCommand.TODAY);

        possibleNames.put("/week", UserCommand.WEEK);
        possibleNames.put("Week", UserCommand.WEEK);
    }

    public UserCommand parse(String command) {
        String trimmedCommand = command.trim();
        return possibleNames.getOrDefault(trimmedCommand, UserCommand.UNKNOWN);
    }
}

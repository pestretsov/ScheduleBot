package org.anstreth.schedulebot.commands;

import java.util.HashMap;
import java.util.Map;

class MenuCommandParser {
    private final Map<String, MenuCommand> possibleNames = new HashMap<>();

    {
        possibleNames.put("/back", MenuCommand.BACK);
        possibleNames.put("Back", MenuCommand.BACK);

        possibleNames.put("/reset_group", MenuCommand.RESET_GROUP);
        possibleNames.put("Reset group", MenuCommand.RESET_GROUP);
    }

    MenuCommand parse(String command) {
        String trimmedCommand = command.trim();
        return possibleNames.getOrDefault(trimmedCommand, MenuCommand.UNKNOWN);
    }

}

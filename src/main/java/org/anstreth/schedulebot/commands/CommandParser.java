package org.anstreth.schedulebot.commands;

import java.util.HashMap;
import java.util.Map;

abstract class CommandParser<T extends Enum<T>> {
    private final Map<String, T> possibleNames = new HashMap<>();

    public T parse(String command) {
        String trimmedCommand = command.trim();
        return possibleNames.getOrDefault(trimmedCommand, getDefault());
    }

    void addCommand(T command, String commandInText, String... otherVariants) {
        possibleNames.put(commandInText, command);
        for (String variant : otherVariants) {
            possibleNames.put(variant, command);
        }
    }

    protected abstract T getDefault();
}

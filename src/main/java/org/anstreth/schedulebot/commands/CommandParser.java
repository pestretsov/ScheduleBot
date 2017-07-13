package org.anstreth.schedulebot.commands;

import java.util.HashMap;
import java.util.Map;

public abstract class CommandParser <T extends Enum<T>> {
    protected final Map<String, T> possibleNames = new HashMap<>();

    public T parse(String command) {
        String trimmedCommand = command.trim();
        return possibleNames.getOrDefault(trimmedCommand, getDefault());
    }

    protected abstract T getDefault();
}

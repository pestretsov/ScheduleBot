package org.anstreth.schedulebot.commands;

import java.util.HashMap;
import java.util.Map;

abstract class CommandParser<T extends Enum<T>> {
    private final Map<String, T> possibleNames = new HashMap<>();
    private static final String EMPTY_ARGUMENT_PLACEHOLDER = "";

    public FullCommand<T> parse(String command) {
        String[] commandWithArgument = command.trim().split(" ");
        String strCommandName = commandWithArgument[0].split("@")[0];
        String commandArgument = commandWithArgument.length > 1 ? commandWithArgument[1] : EMPTY_ARGUMENT_PLACEHOLDER;

        T commandName = possibleNames.getOrDefault(strCommandName, getDefault());

        return new FullCommand<>(commandName, commandArgument);
    }

    void addCommand(T command, String commandInText, String... otherVariants) {
        possibleNames.put(commandInText, command);
        for (String variant : otherVariants) {
            possibleNames.put(variant, command);
        }
    }

    protected abstract T getDefault();
}

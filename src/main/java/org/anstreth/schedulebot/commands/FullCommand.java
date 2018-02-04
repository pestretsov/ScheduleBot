package org.anstreth.schedulebot.commands;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FullCommand<T extends Enum<T>> {
    private T name;
    private String argument;
}

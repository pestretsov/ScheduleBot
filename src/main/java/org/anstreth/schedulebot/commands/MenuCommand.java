package org.anstreth.schedulebot.commands;

/**
 * Enum to represent possible user commands in menu state.
 */

public enum MenuCommand {

    /**
     * Command to get back to normal state.
     */
    BACK,

    /**
     * Command to remove user's group and set his state to NO_GROUP
     */
    RESET_GROUP,

    /**
     * Unknown command.
     */
    UNKNOWN

}

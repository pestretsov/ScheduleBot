package org.anstreth.schedulebot.model;

/**
 * Enum to represent user's state.
 *
 * @author Roman Golyshev
 */
public enum UserState {
    /**
     * First state, means that user just entered the chat and need to be asked for a group number.
     */
    NO_GROUP,

    /**
     * Second state. Means that user already tried to find his group but nothing has been found
     * and he needs to try again.
     */
    NO_GROUP_FOUND,

    /**
     * Third state, means that user's group is successfully found and user can query his schedule.
     */
    WITH_GROUP
}

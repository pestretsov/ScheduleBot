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
     * Second state. Means that user has been asked for group, and expected to send his group number.
     */
    ASKED_FOR_GROUP,

    /**
     * Third state, means that user's group is successfully found and user can query his schedule.
     */
    WITH_GROUP,

    /**
     * User has group and entered the menu.
     */
    MENU
}

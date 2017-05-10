package org.anstreth.schedulebot.exceptions;

public class NoGroupForUserException extends RuntimeException {
    public NoGroupForUserException() {}

    public NoGroupForUserException(long userId) {
        super("No group is specified for user with id " + userId);
    }
}

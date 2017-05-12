package org.anstreth.schedulebot.exceptions;

public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException(long userId) {
        super("No user with id " + userId + "is found");
    }
}

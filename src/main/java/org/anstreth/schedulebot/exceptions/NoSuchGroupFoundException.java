package org.anstreth.schedulebot.exceptions;

public class NoSuchGroupFoundException extends RuntimeException {
    public NoSuchGroupFoundException(String groupName) {
        super("No group is found by name " + groupName);
    }
}

package org.anstreth.schedulebot.exceptions;

public class NoGroupFoundException extends RuntimeException {
    public NoGroupFoundException(String groupName) {
        super("No group is found by name " + groupName);
    }
}

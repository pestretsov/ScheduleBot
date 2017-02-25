package org.anstreth.schedulebot.scheduleuserservice;

@FunctionalInterface
public interface MessageSender {
    void sendMessage(String message);
}


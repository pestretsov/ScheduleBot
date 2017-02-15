package org.anstreth.schedulebot.schedulebottextservice;

@FunctionalInterface
public interface MessageSender {
    void sendMessage(String message);
}


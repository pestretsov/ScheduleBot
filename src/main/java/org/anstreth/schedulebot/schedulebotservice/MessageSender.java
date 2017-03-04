package org.anstreth.schedulebot.schedulebotservice;

@FunctionalInterface
public interface MessageSender {
    void sendMessage(String message);
}


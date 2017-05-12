package org.anstreth.schedulebot.schedulebotservice;

import java.util.Collections;
import java.util.List;

@FunctionalInterface
public interface MessageWithRepliesSender extends MessageSender {
    @Override
    default void sendMessage(String message) {
        sendMessage(message, Collections.emptyList());
    }

    void sendMessage(String message, List<String> replies);

    default MessageSender withReplies(List<String> replies) {
        return message -> sendMessage(message, replies);
    }
}

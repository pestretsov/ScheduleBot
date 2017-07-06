package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.schedulebot.response.BotResponse;

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

    default void sendResponse(BotResponse response) {
        sendMessage(response.getMessage(), response.getResponses());
    }
}

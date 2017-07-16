package org.anstreth.schedulebot.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
public class BotResponse {
    private final List<String> messages;
    private final List<String> replies;
    private final List<List<String>> repliesRows;

    private BotResponse(List<String> messages) {
        this(messages, Collections.emptyList(), Collections.emptyList());
    }

    public BotResponse(String message, List<String> replies) {
        this(Collections.singletonList(message), replies, Collections.singletonList(replies));
    }

    public BotResponse(List<String> messages, List<String> replies) {
        this(messages, replies, Collections.singletonList(replies));
    }

    public BotResponse(String message) {
        this(Collections.singletonList(message));
    }
}

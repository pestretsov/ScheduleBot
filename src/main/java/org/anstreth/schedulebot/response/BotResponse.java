package org.anstreth.schedulebot.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
public class BotResponse {
    private final List<String> messages;
    private final List<List<String>> repliesRows;

    private BotResponse(List<String> messages) {
        this(messages, Collections.emptyList());
    }

    public BotResponse(String message, List<List<String>> replies) {
        this(Collections.singletonList(message), replies);
    }

    public BotResponse(String message) {
        this(Collections.singletonList(message));
    }
}

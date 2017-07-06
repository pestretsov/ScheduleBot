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

    public BotResponse(List<String> messages) {
        this(messages, Collections.emptyList());
    }

    public BotResponse(String messages) {
        this(Collections.singletonList(messages));
    }
}

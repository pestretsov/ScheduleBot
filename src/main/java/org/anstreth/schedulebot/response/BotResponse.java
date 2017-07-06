package org.anstreth.schedulebot.response;

import lombok.Data;

import java.util.List;

@Data
public class BotResponse {
    private final List<String> messages;
    private final List<String> replies;
}

package org.anstreth.schedulebot.response;

import lombok.Data;

import java.util.List;

@Data
public class BotResponse {
    private final String message;
    private final List<String> responses;
}

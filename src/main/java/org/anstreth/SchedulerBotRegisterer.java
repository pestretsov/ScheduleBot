package org.anstreth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;

@Component
public class SchedulerBotRegisterer {

    private final TelegramBotsApi telegramBotsApi;

    private final SchedulerPollingBot schedulerPollingBot;

    @Autowired
    public SchedulerBotRegisterer(TelegramBotsApi telegramBotsApi, SchedulerPollingBot schedulerPollingBot) {
        this.telegramBotsApi = telegramBotsApi;
        this.schedulerPollingBot = schedulerPollingBot;
    }

    @PostConstruct
    public void postConstruct() throws TelegramApiRequestException {
        telegramBotsApi.registerBot(schedulerPollingBot);
    }

}

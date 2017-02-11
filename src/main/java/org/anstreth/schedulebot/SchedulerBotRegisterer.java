package org.anstreth.schedulebot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;

@Component
class SchedulerBotRegisterer {

    private final TelegramBotsApi telegramBotsApi;

    private final SchedulerPollingBot schedulerPollingBot;

    @Autowired
    SchedulerBotRegisterer(TelegramBotsApi telegramBotsApi, SchedulerPollingBot schedulerPollingBot) {
        this.telegramBotsApi = telegramBotsApi;
        this.schedulerPollingBot = schedulerPollingBot;
    }

    @PostConstruct
    void postConstruct() throws TelegramApiRequestException {
        telegramBotsApi.registerBot(schedulerPollingBot);
    }

}

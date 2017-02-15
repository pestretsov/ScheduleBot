package org.anstreth.schedulebot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.generics.BotSession;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
class SchedulerBotRegisterer {

    private final TelegramBotsApi telegramBotsApi;

    private final SchedulerPollingBot schedulerPollingBot;

    private BotSession botSession;

    @Autowired
    SchedulerBotRegisterer(TelegramBotsApi telegramBotsApi, SchedulerPollingBot schedulerPollingBot) {
        this.telegramBotsApi = telegramBotsApi;
        this.schedulerPollingBot = schedulerPollingBot;
    }

    @PostConstruct
    void postConstruct() throws TelegramApiRequestException {
        botSession = telegramBotsApi.registerBot(schedulerPollingBot);
    }

    @PreDestroy
    private void tearDown() {
        botSession.close();
    }

}

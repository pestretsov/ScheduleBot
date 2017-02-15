package org.anstreth.schedulebot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.generics.BotSession;

import javax.annotation.PreDestroy;

@Component
class SchedulerBotRegisterer implements ApplicationRunner {

    private final TelegramBotsApi telegramBotsApi;

    private final SchedulerPollingBot schedulerPollingBot;

    private BotSession botSession;

    @Autowired
    SchedulerBotRegisterer(TelegramBotsApi telegramBotsApi, SchedulerPollingBot schedulerPollingBot) {
        this.telegramBotsApi = telegramBotsApi;
        this.schedulerPollingBot = schedulerPollingBot;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        botSession = telegramBotsApi.registerBot(schedulerPollingBot);
    }

    @PreDestroy
    private void tearDown() {
        botSession.close();
    }
}
package org.anstreth.schedulebot;

import org.anstreth.schedulebot.schedulebottextservice.SchedulerBotTextService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn("telegramBotsApi")
class SchedulerBotProvider {

    @Value("${bot.token}")
    private String BOT_TOKEN;

    @Bean
    SchedulerPollingBot schedulerPollingBot(SchedulerBotTextService schedulerBotTextService) {
        return new SchedulerPollingBot(BOT_TOKEN, schedulerBotTextService);
    }
}

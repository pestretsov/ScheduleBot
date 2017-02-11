package org.anstreth.schedulebot;

import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.anstreth.schedulebot.schedulerrepository.SchedulerRepository;
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
    SchedulerPollingBot schedulerPollingBot(SchedulerRepository schedulerRepository, SchedulerFormatter schedulerFormatter) {
        return new SchedulerPollingBot(BOT_TOKEN, schedulerRepository, schedulerFormatter);
    }
}

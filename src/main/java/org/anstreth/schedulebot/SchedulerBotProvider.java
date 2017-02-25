package org.anstreth.schedulebot;

import org.anstreth.schedulebot.scheduleuserservice.SchedulerUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;

@Configuration
@DependsOn("telegramBotsApi")
@PropertySource("classpath:bot.properties")
class SchedulerBotProvider {

    @Value("${bot.token}")
    private String BOT_TOKEN;

    @Bean
    SchedulerPollingBot schedulerPollingBot(SchedulerUserService schedulerUserService) {
        return new SchedulerPollingBot(BOT_TOKEN, schedulerUserService);
    }
}

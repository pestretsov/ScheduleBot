package org.anstreth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn("telegramBotsApi")
public class SchedulerBotProvider {

    @Value("${bot.token}")
    private String BOT_TOKEN;

    @Bean
    SchedulerPollingBot schedulerPollingBot() {
        return new SchedulerPollingBot(BOT_TOKEN);
    }
}

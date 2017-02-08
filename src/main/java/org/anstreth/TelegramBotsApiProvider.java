package org.anstreth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;

@Configuration
public class TelegramBotsApiProvider {

    @Bean
    TelegramBotsApi telegramBotsApi() {
        ApiContextInitializer.init();
        return new TelegramBotsApi();
    }

}

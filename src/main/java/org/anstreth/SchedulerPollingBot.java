package org.anstreth;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

class SchedulerPollingBot extends TelegramLongPollingBot {

    private final String token;

    SchedulerPollingBot(String token) {
        super();
        this.token = token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(update.getMessage().getChatId())
                .setText("This bot is not implemented yet!");
        try {
            sendMessage(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getBotUsername() {
        return "SchedulerBot";
    }

    @Override
    public String getBotToken() {
        return token;
    }

}

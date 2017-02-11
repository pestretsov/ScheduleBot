package org.anstreth.schedulebot;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

class SchedulerPollingBot extends TelegramLongPollingBot {

    private final String token;

    private final SchedulerBotTextService schedulerBotTextService;

    SchedulerPollingBot(String token, SchedulerBotTextService schedulerBotTextService) {
        super();
        this.token = token;
        this.schedulerBotTextService = schedulerBotTextService;
    }

    @Override
    public String getBotUsername() {
        return "SchedulerBot";
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (updateHasMessageWithText(update)) {
            try {
                sendMessage(getReplyForUpdate(update));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean updateHasMessageWithText(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }

    private SendMessage getReplyForUpdate(Update update) {
        return new SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(update.getMessage().getChatId())
                .setText(schedulerBotTextService.getReplyForText(update.getMessage().getText()));
    }

}

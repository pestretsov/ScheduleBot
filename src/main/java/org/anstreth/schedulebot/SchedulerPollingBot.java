package org.anstreth.schedulebot;

import org.anstreth.schedulebot.schedulebottextservice.MessageSender;
import org.anstreth.schedulebot.schedulebottextservice.SchedulerBotTextService;
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
            Long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();
            MessageSender messageSender = getMessageSenderForChatId(chatId);
            schedulerBotTextService.handleText(messageText, messageSender);
        }
    }

    private boolean updateHasMessageWithText(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }

    private MessageSender getMessageSenderForChatId(Long chatId) {
        return (message) -> sendMessageQuietly(getSendMessageForChatIdWithText(chatId, message));
    }

    private void sendMessageQuietly(SendMessage sendMessageForChatWithText) {
        try {
            sendMessage(sendMessageForChatWithText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private SendMessage getSendMessageForChatIdWithText(Long chatId, String message) {
        return new SendMessage().setChatId(chatId).setText(message);
    }

}

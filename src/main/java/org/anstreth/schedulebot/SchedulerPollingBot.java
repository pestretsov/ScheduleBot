package org.anstreth.schedulebot;

import lombok.extern.log4j.Log4j;
import org.anstreth.schedulebot.schedulebotservice.MessageSender;
import org.anstreth.schedulebot.schedulebotservice.SchedulerBotService;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Log4j
class SchedulerPollingBot extends TelegramLongPollingBot {

    private final String token;

    private final SchedulerBotService schedulerBotService;

    SchedulerPollingBot(String token, SchedulerBotService schedulerBotService) {
        super();
        this.token = token;
        this.schedulerBotService = schedulerBotService;
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
            UserRequest userRequest = getUserRequestFromUpdate(update);
            MessageSender messageSender = getMessageSenderForChatId(chatId);
            schedulerBotService.handleRequest(userRequest, messageSender);
        }
    }

    private boolean updateHasMessageWithText(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }

    private UserRequest getUserRequestFromUpdate(Update update) {
        Long chatId = update.getMessage().getChatId();
        String messageText = update.getMessage().getText();
        return new UserRequest(chatId, messageText);
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

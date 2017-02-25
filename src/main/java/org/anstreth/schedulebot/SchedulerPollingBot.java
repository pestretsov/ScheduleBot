package org.anstreth.schedulebot;

import lombok.extern.log4j.Log4j;
import org.anstreth.schedulebot.scheduleuserservice.MessageSender;
import org.anstreth.schedulebot.scheduleuserservice.SchedulerUserService;
import org.anstreth.schedulebot.scheduleuserservice.request.UserRequest;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Log4j
class SchedulerPollingBot extends TelegramLongPollingBot {

    private final String token;

    private final SchedulerUserService schedulerUserService;

    SchedulerPollingBot(String token, SchedulerUserService schedulerUserService) {
        super();
        this.token = token;
        this.schedulerUserService = schedulerUserService;
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
            schedulerUserService.handleRequest(userRequest, messageSender);
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

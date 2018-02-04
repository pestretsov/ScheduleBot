package org.anstreth.schedulebot;

import lombok.extern.log4j.Log4j;
import org.anstreth.schedulebot.response.BotResponse;
import org.anstreth.schedulebot.schedulebotservice.UserRequestRouter;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.List;
import java.util.stream.Collectors;

@Log4j
class SchedulerPollingBot extends TelegramLongPollingBot {

    private final String token;

    private final UserRequestRouter userRequestRouter;

    SchedulerPollingBot(String token, UserRequestRouter userRequestRouter) {
        super();
        this.token = token;
        this.userRequestRouter = userRequestRouter;
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
            userRequestRouter.routeAsync(userRequest)
                    .thenAccept(response -> sendBotResponseToChat(chatId, response));
        }
    }

    private boolean updateHasMessageWithText(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }

    private UserRequest getUserRequestFromUpdate(Update update) {
        Long chatId = update.getMessage().getChatId();
        String messageText = update.getMessage().getText();
        boolean isGroupChat = update.getMessage().isGroupMessage() || update.getMessage().isSuperGroupMessage();
        return new UserRequest(chatId, messageText, isGroupChat);
    }

    private void sendBotResponseToChat(Long chatId, BotResponse botResponse) {
        getSendMessagesForBotResponse(chatId, botResponse)
                .forEach(this::sendMessageQuietly);
    }

    private List<SendMessage> getSendMessagesForBotResponse(Long chatId, BotResponse botResponse) {
        return botResponse.getMessages().stream()
                .map(message -> getSendMessage(chatId, message))
                .collect(Collectors.toList());
    }

    private void sendMessageQuietly(SendMessage sendMessageForChatWithText) {
        try {
            execute(sendMessageForChatWithText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private SendMessage getSendMessage(Long chatId, String message) {
        return new SendMessage()
                .setChatId(chatId)
                .setText(message);
    }
}

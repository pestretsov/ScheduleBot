package org.anstreth.schedulebot;

import lombok.extern.log4j.Log4j;
import org.anstreth.schedulebot.response.BotResponse;
import org.anstreth.schedulebot.schedulebotservice.SchedulerBotService;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
            schedulerBotService.handleRequestAsync(userRequest)
                    .thenAccept(response -> sendBotResponseToChat(chatId, response));
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

    private void sendBotResponseToChat(Long chatId, BotResponse botResponse) {
        getSendMessagesForBotResponse(chatId, botResponse)
                .forEach(this::sendMessageQuietly);
    }

    private List<SendMessage> getSendMessagesForBotResponse(Long chatId, BotResponse botResponse) {
        return botResponse.getMessages().stream()
                .map(message -> getSendMessage(chatId, message, botResponse.getRepliesRows()))
                .collect(Collectors.toList());
    }

    private void sendMessageQuietly(SendMessage sendMessageForChatWithText) {
        try {
            sendMessage(sendMessageForChatWithText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private SendMessage getSendMessage(Long chatId, String message, List<List<String>> replies) {
        return new SendMessage()
                .setChatId(chatId)
                .setText(message)
                .setReplyMarkup(getReplyMarkup(replies));
    }

    private ReplyKeyboard getReplyMarkup(List<List<String>> replies) {
        if (replies.isEmpty()) return new ReplyKeyboardRemove();

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(getRowsWithReplies(replies));
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    private List<KeyboardRow> getRowsWithReplies(List<List<String>> replies) {
        return replies.stream()
                .map(this::keyboardRowWithReplies)
                .collect(Collectors.toList());
    }

    private KeyboardRow keyboardRowWithReplies(List<String> replies) {
        KeyboardRow keyboardButtons = new KeyboardRow();
        replies.forEach(keyboardButtons::add);
        return keyboardButtons;
    }

}

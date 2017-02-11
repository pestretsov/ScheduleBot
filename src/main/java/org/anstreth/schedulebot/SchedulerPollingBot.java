package org.anstreth.schedulebot;

import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.anstreth.schedulebot.schedulerrepository.SchedulerRepository;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.NoSuchElementException;

class SchedulerPollingBot extends TelegramLongPollingBot {

    private final String token;

    private final SchedulerRepository schedulerRepository;

    private final SchedulerFormatter schedulerFormatter;

    SchedulerPollingBot(String token, SchedulerRepository schedulerRepository, SchedulerFormatter schedulerFormatter) {
        super();
        this.token = token;
        this.schedulerRepository = schedulerRepository;
        this.schedulerFormatter = schedulerFormatter;
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

        if (updateHasTodayCommand(update)) {
            try {
                sendMessage(getTodayScheduleMessage(update));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }
    }

    private SendMessage getTodayScheduleMessage(Update update) {
        return new SendMessage() // Create a SendMessage object with mandatory fields
                        .setChatId(update.getMessage().getChatId())
                        .setText(getTodayScheduleText());
    }

    private boolean updateHasTodayCommand(Update update) {
        return updateHasCommand(update, "/today");
    }

    private boolean updateHasCommand(Update update, String command) {
        return update.hasMessage()
                && update.getMessage().hasText()
                && update.getMessage().getText().equals(command);
    }

    private String getTodayScheduleText() {
        try {
            return schedulerFormatter.formatDay(schedulerRepository.getScheduleForToday());
        } catch (NoSuchElementException e) {
            return "Today is sunday, there are no lessons!";
        }
    }

}

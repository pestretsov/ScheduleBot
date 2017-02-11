package org.anstreth.schedulebot;

import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.anstreth.schedulebot.schedulerrepository.SchedulerRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
                        .setText(getText());
    }

    private boolean updateHasTodayCommand(Update update) {
        return update.hasMessage()
                && update.getMessage().hasText()
                && update.getMessage().getText().equals("/today");
    }

    private String getText() {
        try {
            return schedulerFormatter.formatDay(schedulerRepository.getScheduleForToday());
        } catch (NoSuchElementException e) {
            return "Today is sunday, there are no lessons!";
        }
    }

}

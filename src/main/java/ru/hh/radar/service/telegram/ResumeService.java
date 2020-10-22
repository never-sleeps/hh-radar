package ru.hh.radar.service.telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public interface ResumeService {
    List<SendMessage> showAllResume(Update update) throws TelegramApiException;

    SendMessage showPublishResumeMenu(Update update) throws TelegramApiException;

    SendMessage publishResume(Update update) throws TelegramApiException;
}

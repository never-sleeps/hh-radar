package ru.hh.radar.telegram.service;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface TelegramService {
    Message getMessage(Update update) throws TelegramApiException;

    User getFrom(Update update) throws TelegramApiException;
}

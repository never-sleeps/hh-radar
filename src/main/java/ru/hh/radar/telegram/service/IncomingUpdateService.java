package ru.hh.radar.telegram.service;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.model.TelegramUserInfo;

public interface IncomingUpdateService {
    Message getMessage(Update update) throws TelegramApiException;

    User getFrom(Update update) throws TelegramApiException;

    String getCommand(Update update) throws TelegramApiException;

    Long getChatId(Update update) throws TelegramApiException;

    Integer getMessageId(Update update) throws TelegramApiException;

    String getLanguageCode(Update update);

    Long getUserId(Update update) throws TelegramApiException;

    TelegramUserInfo getTelegramUserInfo(Update update) throws TelegramApiException;
}

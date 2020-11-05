package ru.hh.radar.telegram.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.telegram.service.IncomingUpdateService;

@Slf4j
@Service
public class IncomingUpdateServiceImpl implements IncomingUpdateService {
    @Override
    public Message getMessage(Update update) throws TelegramApiException {
        if(update.hasMessage())
            return update.getMessage();
        else if(update.hasCallbackQuery())
            return update.getCallbackQuery().getMessage();
        throw new TelegramApiException("Error determining Message");
    }

    @Override
    public User getFrom(Update update) throws TelegramApiException {
        if(update.hasMessage())
            return update.getMessage().getFrom();
        else if(update.hasCallbackQuery())
            return update.getCallbackQuery().getFrom();
        throw new TelegramApiException("Error determining From");
    }

    @Override
    public String getCommand(Update update) throws TelegramApiException {
        if (update.hasMessage() && update.getMessage().hasText()) {
            return update.getMessage().getText().trim();
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getData().trim();
        }
        throw new TelegramApiException("Error determining command");
    }

    @Override
    public Long getChatId(Update update) throws TelegramApiException {
        return getMessage(update).getChatId();
    }

    @Override
    public Integer getMessageId(Update update) throws TelegramApiException {
        return getMessage(update).getMessageId();
    }

    @Override
    public String getLanguageCode(Update update) {
        try {
            return getFrom(update).getLanguageCode();
        } catch (Exception e) {
            log.error("LanguageCode not found. Will be used default 'ru'");
            return "ru";
        }
    }

    @Override
    public String getUserName(Update update) throws TelegramApiException {
        return getMessage(update).getChat().getUserName();
    }

    @Override
    public String getValueFromCallbackQuery(Update update) {
        String callbackQueryData = update.getCallbackQuery().getData();
        return callbackQueryData.substring(callbackQueryData.lastIndexOf(".") + 1);
    }

    @Override
    public String getLongValueFromCallbackQuery(Update update) {
        String callbackQueryData = update.getCallbackQuery().getData();
        int indexOfPostfix = callbackQueryData.lastIndexOf(".");
        String indexOfPoint = callbackQueryData.substring(0, indexOfPostfix);
        int indexOfPrefix = indexOfPoint.lastIndexOf(".");

        return callbackQueryData.substring(indexOfPrefix + 1);
    }
}

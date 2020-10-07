package ru.hh.radar.telegram.service.impl;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.telegram.service.TelegramService;

@Service
public class TelegramServiceImpl implements TelegramService {
    @Override
    public Message getMessage(Update update) throws TelegramApiException {
        if(update.hasMessage())
            return update.getMessage();
        else if(update.hasCallbackQuery())
            return update.getCallbackQuery().getMessage();
        throw new TelegramApiException("Error determining Message");
    }
}

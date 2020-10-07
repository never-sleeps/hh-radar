package ru.hh.radar.baseVersionTelegram.handler;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.telegram.TelegramBot;

@Deprecated
public interface RequestHandler {

    void handle(String text, Update update, TelegramBot bot) throws TelegramApiException;
}

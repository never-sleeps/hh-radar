package ru.hh.radar.telegram.handler;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.telegram.TelegramBot;

import java.util.Map;

public interface RequestHandler {

    void handle(String text, Update update, Map<Long, String> searchMap, TelegramBot bot) throws TelegramApiException;
}

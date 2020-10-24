package ru.hh.radar.telegram.service;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public interface ExecutorService {
    List<BotApiMethod<?>> getExecutors(Update update) throws TelegramApiException;
}

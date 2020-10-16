package ru.hh.radar.service.telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface SearchService {

    SendMessage showSearchMenu(Update update) throws TelegramApiException;

    SendMessage showExperienceMenu(Update update) throws TelegramApiException;

    SendMessage showEmploymentMenu(Update update) throws TelegramApiException;

    SendMessage showScheduleMenu(Update update) throws TelegramApiException;

    SendMessage setExperienceSearchParameters(Update update) throws TelegramApiException;
}

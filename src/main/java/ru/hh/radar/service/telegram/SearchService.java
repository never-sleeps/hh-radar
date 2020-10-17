package ru.hh.radar.service.telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.dto.SearchParameters;

import java.util.List;

public interface SearchService {

    SendMessage showSearchMenu(Update update) throws TelegramApiException;

    SendMessage showExperienceMenu(Update update) throws TelegramApiException;

    SendMessage showEmploymentMenu(Update update) throws TelegramApiException;

    SendMessage showScheduleMenu(Update update) throws TelegramApiException;

    SendMessage showAreaMenu(Update update) throws TelegramApiException;

    SendMessage showSpecializationMenu(Update update) throws TelegramApiException;

    SendMessage setSearchParameters(SearchParameters.SearchParam searchParam, Update update) throws TelegramApiException;

    List<SendMessage> runSearch(Update update) throws TelegramApiException;
}

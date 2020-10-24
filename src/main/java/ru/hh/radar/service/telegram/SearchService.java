package ru.hh.radar.service.telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.model.SearchParameters.SearchParam;

import java.util.List;

public interface SearchService {

    SendMessage showSearchMenu(String lang);

    SendMessage setSearchParameters(SearchParam searchParam, String lang, String value) throws TelegramApiException;

    SendMessage showSearchParameters(String lang) throws TelegramApiException;

    List<SendMessage> runSearch(String lang) throws TelegramApiException;
}

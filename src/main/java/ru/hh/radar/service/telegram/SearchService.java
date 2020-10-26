package ru.hh.radar.service.telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.hh.radar.model.entity.SearchParameters;

import java.util.List;

public interface SearchService {

    List<SendMessage> runSearch(SearchParameters parameters, String lang);
}

package ru.hh.radar.service.telegram.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.dto.VacancyDTO;
import ru.hh.radar.model.SearchParameters;
import ru.hh.radar.model.SearchParameters.SearchParam;
import ru.hh.radar.service.common.UserService;
import ru.hh.radar.service.hh.HhVacancyService;
import ru.hh.radar.service.telegram.SearchService;
import ru.hh.radar.telegram.service.InlineKeyboardService;
import ru.hh.radar.telegram.service.MessageService;
import ru.hh.radar.telegram.service.TelegramMessageService;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final TelegramMessageService tgmMessageService;
    private final HhVacancyService hhVacancyService;
    private final InlineKeyboardService inlineKeyboardService;

    private final MessageService msg;
    private final UserService userService;

    private static SearchParameters searchParameters = new SearchParameters();

    @Override
    public SendMessage showSearchMenu(String lang) {
        return tgmMessageService.createMenuMessage(
                "️✏️",
                inlineKeyboardService.getMainSearchMenu(lang)
        );
    }

    public SendMessage showItSpecializationMenu(String lang) throws TelegramApiException {
        return tgmMessageService.createButtonMessage(
                inlineKeyboardService.getItSpecializationMenu(lang)
        );
    }

    @Override
    public SendMessage setSearchParameters(SearchParam searchParam, String lang, String value) throws TelegramApiException {
        searchParameters.put(searchParam, value);

        if (searchParam == SearchParam.SPECIALIZATION && value.equals("1")) {
            return showItSpecializationMenu(lang);
        } else {
            return showSearchParameters(lang);
        }
    }

    @Override
    public SendMessage showSearchParameters(String lang) throws TelegramApiException {
        return new SendMessage()
                .setText(toFormatString(lang));
    }

    @Override
    public List<SendMessage> runSearch(String lang) throws TelegramApiException {
        List<VacancyDTO> vacancies = hhVacancyService.getVacancies(searchParameters).getItems();
        searchParameters = new SearchParameters();
        return tgmMessageService.createVacancyMessages(
                msg.getMessage("browser.open", lang),
                vacancies
        );
    }

    private String toFormatString(String lang) {
        StringBuilder stringBuffer = new StringBuilder();
        for(Map.Entry<SearchParameters.SearchParam, String> item : searchParameters.get().entrySet()){
            stringBuffer
                    .append(msg.getMessage(item.getKey().getParam(), lang))
                    .append(": ")
                    .append(item.getValue())
                    .append("\n");
        }
        return stringBuffer.toString();
    }
}
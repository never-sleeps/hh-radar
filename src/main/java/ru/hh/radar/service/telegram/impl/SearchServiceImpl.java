package ru.hh.radar.service.telegram.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.dto.SearchParameters;
import ru.hh.radar.dto.SearchParameters.SearchParam;
import ru.hh.radar.dto.VacancyDTO;
import ru.hh.radar.service.hh.HhVacancyService;
import ru.hh.radar.service.telegram.InlineKeyboardService;
import ru.hh.radar.service.telegram.SearchService;
import ru.hh.radar.service.common.UserService;
import ru.hh.radar.telegram.service.*;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final TelegramMessageService tgmMessageService;
    private final TelegramLocaleElementService tgmLocaleElementService;
    private final HhVacancyService hhVacancyService;
    private final InlineKeyboardService inlineKeyboardService;

    private final MessageService msg;
    private final UserService userService;

    private static SearchParameters searchParameters = new SearchParameters();

    @Override
    public SendMessage showSearchMenu(Update update) throws TelegramApiException {
        String lang = userService.getLocaleForAnswerToUser(update);
        return tgmMessageService.createMenuMessage(
                userService.findUser(update).getChatId(),
                "️✏️",
                inlineKeyboardService.getMainSearchMenu(lang)
        );
    }

    @Override
    public SendMessage showExperienceMenu(Update update) throws TelegramApiException {
        String lang = userService.getLocaleForAnswerToUser(update);
        return tgmMessageService.createButtonMessage(
                userService.findUser(update).getChatId(),
                inlineKeyboardService.getExperienceMenu(lang)
        );
    }

    @Override
    public SendMessage showEmploymentMenu(Update update) throws TelegramApiException {
        String lang = userService.getLocaleForAnswerToUser(update);
        return tgmMessageService.createButtonMessage(
                userService.findUser(update).getChatId(),
                inlineKeyboardService.getEmploymentMenu(lang)
        );
    }

    @Override
    public SendMessage showScheduleMenu(Update update) throws TelegramApiException {
        String lang = userService.getLocaleForAnswerToUser(update);
        return tgmMessageService.createButtonMessage(
                userService.findUser(update).getChatId(),
                inlineKeyboardService.getScheduleMenu(lang)
        );
    }

    @Override
    public SendMessage showAreaMenu(Update update) throws TelegramApiException {
        String lang = userService.getLocaleForAnswerToUser(update);
        return tgmMessageService.createButtonMessage(
                userService.findUser(update).getChatId(),
                inlineKeyboardService.getAreaMenu(lang)
        );
    }

    @Override
    public SendMessage showSpecializationMenu(Update update) throws TelegramApiException {
        String lang = userService.getLocaleForAnswerToUser(update);
        return tgmMessageService.createButtonMessage(
                userService.findUser(update).getChatId(),
                inlineKeyboardService.getSpecializationMenu(lang)
        );
    }

    public SendMessage showItSpecializationMenu(Update update) throws TelegramApiException {
        String lang = userService.getLocaleForAnswerToUser(update);
        return tgmMessageService.createButtonMessage(
                userService.findUser(update).getChatId(),
                inlineKeyboardService.getItSpecializationMenu(lang)
        );
    }

    @Override
    public SendMessage setSearchParameters(SearchParam searchParam, Update update, boolean isShort) throws TelegramApiException {
        String value = (isShort) ? tgmLocaleElementService.getValueFromCallbackQuery(update)
                : tgmLocaleElementService.getLongValueFromCallbackQuery(update);
        searchParameters.put(searchParam, value);

        if (searchParam == SearchParam.SPECIALIZATION && value.equals("1")) {
            return showItSpecializationMenu(update);
        } else {
            return showSearchParameters(update);
        }
    }

    @Override
    public SendMessage showSearchParameters(Update update) throws TelegramApiException {
        String lang = userService.getLocaleForAnswerToUser(update);
        return new SendMessage()
                .setChatId(userService.findUser(update).getChatId())
                .setText(toFormatString(lang));
    }

    @Override
    public List<SendMessage> runSearch(Update update) throws TelegramApiException {
        List<VacancyDTO> vacancies = hhVacancyService.getVacancies(searchParameters).getItems();
        searchParameters = new SearchParameters();
        return tgmMessageService.createVacancyMessages(
                userService.findUser(update).getChatId(),
                msg.getMessage("browser.open", userService.getLocaleForAnswerToUser(update)),
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
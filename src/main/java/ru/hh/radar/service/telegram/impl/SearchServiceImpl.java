package ru.hh.radar.service.telegram.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.model.SearchParameters;
import ru.hh.radar.model.SearchParameters.SearchParam;
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

    /*
    Здесь я либо не заметил, либо мы не учитываем случай если будет несколько пользователей, и начнут друг-другу параметры поиска перезаписывать :)
Это как раз один из кандидатов что можно хранить в базе - параметры поиска нужно привязывать к конкретному пользователю, и наш бот будет помнить последний поиск пользователя.
     */
    private static SearchParameters searchParameters = new SearchParameters();

    /*
    Функционал меню так и просится вынести его в какой-нибудь MenuService, он очень простой - получили запрос и дали какой-то ответ, ну и это не Search
    Поидее этот функционал это вообще работа контроллера и можно смело все перенести в него,
    но тут вам решать :) Облегчить работу контроллера отдельным сервисом тоже можно
    Кроме того разгрузим сервис, сейчас у него в обязанностях и UI показывать, и вакансии искать
     */
    @Override
    public SendMessage showSearchMenu(Update update) throws TelegramApiException {
        String lang = userService.getLocaleForAnswerToUser(update);
        return tgmMessageService.createMenuMessage(//
                userService.findUser(update).getChatId(),
                "️✏️",
                inlineKeyboardService.getMainSearchMenu(lang)
        );
    }

    @Override
    public SendMessage showExperienceMenu(Update update) throws TelegramApiException {
        String lang = userService.getLocaleForAnswerToUser(update);
        return tgmMessageService.createButtonMessage(
                /*
                У нас получается что мы сохраняем пользователя в бд, записываем его chatId, и потом каждый раз тянем его из базы, чтобы узнать этот самый chatId, хотя он приходит нам каждый раз в update.getMessage().getChatId()
В данном случае бот вообще ведет себя как stateless, т.е. в принципе какая разница кто и из какого чата запросил меню, мы просто отвечаем туда откуда получили запрос, как в рест сервисе
                 */
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

    /*
Собственно функционал поиска оставляем здесь, но убираем приведение к SendMessage.
Т.е. отдельно нашли список вакансий, потом в контроллере/отдельном сервисе, обернули это в формат телеграмма,
чтобы у одного сервиса была одна обязанность. Так гораздо проще жить и тестировать
     */

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

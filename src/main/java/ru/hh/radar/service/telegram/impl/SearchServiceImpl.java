package ru.hh.radar.service.telegram.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.dto.SearchParameters;
import ru.hh.radar.dto.VacancyDTO;
import ru.hh.radar.service.hh.HhVacancyService;
import ru.hh.radar.service.telegram.SearchService;
import ru.hh.radar.service.common.UserService;
import ru.hh.radar.telegram.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final TelegramElementService tgmElementService;
    private final TelegramMessageService tgmMessageService;
    private final TelegramLocaleElementService tgmLocaleElementService;
    private final HhVacancyService hhVacancyService;

    private final MessageService msg;
    private final UserService userService;

    private static SearchParameters searchParameters = new SearchParameters();

    @Override
    public SendMessage showSearchMenu(Update update) throws TelegramApiException {
        String languageCode = userService.getLocaleForAnswerToUser(update);
        return tgmMessageService.createMenuMessage(
                userService.findUser(update).getChatId(),
                "️✏️",
                getMainSearchMenu(languageCode)
        );
    }

    @Override
    public SendMessage showExperienceMenu(Update update) throws TelegramApiException {
        String languageCode = userService.getLocaleForAnswerToUser(update);
        return tgmMessageService.createButtonMessage(
                userService.findUser(update).getChatId(),
                getExperienceMenu(languageCode)
        );
    }

    @Override
    public SendMessage showEmploymentMenu(Update update) throws TelegramApiException {
        String languageCode = userService.getLocaleForAnswerToUser(update);
        return tgmMessageService.createButtonMessage(
                userService.findUser(update).getChatId(),
                getEmploymentMenu(languageCode)
        );
    }

    @Override
    public SendMessage showScheduleMenu(Update update) throws TelegramApiException {
        String languageCode = userService.getLocaleForAnswerToUser(update);
        return tgmMessageService.createButtonMessage(
                userService.findUser(update).getChatId(),
                getScheduleMenu(languageCode)
        );
    }

    @Override
    public SendMessage showAreaMenu(Update update) throws TelegramApiException {
        String languageCode = userService.getLocaleForAnswerToUser(update);
        return tgmMessageService.createButtonMessage(
                userService.findUser(update).getChatId(),
                getAreaMenu(languageCode)
        );
    }

    @Override
    public SendMessage showSpecializationMenu(Update update) throws TelegramApiException {
        String languageCode = userService.getLocaleForAnswerToUser(update);
        return tgmMessageService.createButtonMessage(
                userService.findUser(update).getChatId(),
                getSpecializationMenu(languageCode)
        );
    }

    @Override
    public SendMessage setSearchParameters(SearchParameters.SearchParam searchParam, Update update) throws TelegramApiException {
        searchParameters.put(
                searchParam,
                tgmLocaleElementService.getValueFromCallbackQueryData(update)
        );
        String languageCode = userService.getLocaleForAnswerToUser(update);
        return new SendMessage()
                .setChatId(userService.findUser(update).getChatId())
                .setText(toFormatString(languageCode));
    }

    @Override
    public List<SendMessage> runSearch(Update update) throws TelegramApiException {
        List<VacancyDTO> vacancies = hhVacancyService.getVacancies(searchParameters).getItems();

        return tgmMessageService.createVacancyMessages(
                userService.findUser(update).getChatId(),
                msg.getMessage("browser.open", userService.getLocaleForAnswerToUser(update)),
                vacancies
        );
    }

    private ReplyKeyboardMarkup getMainSearchMenu(String language) {
        List<KeyboardRow> list = tgmElementService.createKeyboardRow(
                tgmElementService.createKeyboardRow(
                        msg.getMessage("search.specialization", language),
                        msg.getMessage("search.area", language)
                ),
                tgmElementService.createKeyboardRow(
                        msg.getMessage("search.experience", language),
                        msg.getMessage("search.employment", language),
                        msg.getMessage("search.schedule", language)
                ),
                tgmElementService.createKeyboardRow(
                        msg.getMessage("search.run", language),
                        msg.getMessage("cancel", language)
                )
        );
        return tgmElementService.createReplyKeyboardMarkup(list);
    }

    private InlineKeyboardMarkup getExperienceMenu(String lang) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.experience.noExperience" , lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.experience.between1And3", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.experience.between3And6", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.experience.moreThan6", lang)
                )
        );
        return tgmElementService.createInlineKeyboardMarkup(rowsInline);
    }

    private InlineKeyboardMarkup getEmploymentMenu(String lang) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.employment.full", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.employment.part", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.employment.project", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.employment.volunteer", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.employment.probation", lang)
                )
        );
        return tgmElementService.createInlineKeyboardMarkup(rowsInline);
    }

    private InlineKeyboardMarkup getScheduleMenu(String lang) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.schedule.fullDay", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.schedule.shift", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.schedule.flexible", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.schedule.remote", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.schedule.flyInFlyOut", lang)
                )
        );
        return tgmElementService.createInlineKeyboardMarkup(rowsInline);
    }

    private InlineKeyboardMarkup getAreaMenu(String lang) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.area.1", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.area.113", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.area.1438", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.area.88", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.area.1202", lang)
                )
        );
        return tgmElementService.createInlineKeyboardMarkup(rowsInline);
    }

    private InlineKeyboardMarkup getSpecializationMenu(String lang) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.1", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.2", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.3", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.4", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.5", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.6", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.7", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.8", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.9", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.10", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.11", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.12", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.13", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.14", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.15", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.16", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.17", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.18", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.19", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.20", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.21", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.22", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.23", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.24", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.25", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.26", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.27", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.29", lang)
                )
        );
        return tgmElementService.createInlineKeyboardMarkup(rowsInline);
    }

    private String toFormatString(String languageCode) {
        StringBuilder stringBuffer = new StringBuilder();
        for(Map.Entry<SearchParameters.SearchParam, String> item : searchParameters.get().entrySet()){
            stringBuffer
                    .append(msg.getMessage(item.getKey().getParam(), languageCode))
                    .append(": ")
                    .append(item.getValue())
                    .append("\n");
        }
        return stringBuffer.toString();
    }
}
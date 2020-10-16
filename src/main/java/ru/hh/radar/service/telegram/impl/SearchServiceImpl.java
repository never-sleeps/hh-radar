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
import ru.hh.radar.model.User;
import ru.hh.radar.service.telegram.SearchService;
import ru.hh.radar.service.common.UserService;
import ru.hh.radar.telegram.service.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final UserService userService;
    private final TelegramElementService tgmElementService;
    private final TelegramMessageService tgmMessageService;
    private final TelegramLocaleElementService tgmLocaleElementService;
    private final MessageService msg;


    private static SearchParameters searchParameters = new SearchParameters();

    @Override
    public SendMessage showSearchMenu(Update update) throws TelegramApiException {
        String languageCode = userService.getLocaleForAnswerToUser(update);
        return tgmMessageService.createMenuMessage(
                userService.findUser(update).getChatId(),
                "-",
                getMainSearchMenu(languageCode)
        );
    }

    @Override
    public SendMessage showExperienceMenu(Update update) throws TelegramApiException {
        String languageCode = userService.getLocaleForAnswerToUser(update);
        return tgmMessageService.createButtonMessage(
                userService.findUser(update).getChatId(),
                "-",
                getExperienceMenu(languageCode)
        );
    }

    @Override
    public SendMessage showEmploymentMenu(Update update) throws TelegramApiException {
        String languageCode = userService.getLocaleForAnswerToUser(update);
        return tgmMessageService.createButtonMessage(
                userService.findUser(update).getChatId(),
                "-",
                getEmploymentMenu(languageCode)
        );
    }

    private ReplyKeyboardMarkup getMainSearchMenu(String language) {
        List<KeyboardRow> list = tgmElementService.createKeyboardRow(
                tgmElementService.createKeyboardRow(
                        msg.getMessage("search.text", language),
                        msg.getMessage("search.specialization", language),
                        msg.getMessage("search.area", language)
                ),
                tgmElementService.createKeyboardRow(
                        msg.getMessage("search.experience", language),
                        msg.getMessage("search.employment", language),
                        msg.getMessage("search.schedule", language),
                        msg.getMessage("search.salary", language)
                )
        );
        return tgmElementService.createReplyKeyboardMarkup(list);
    }

    @Override
    public SendMessage showScheduleMenu(Update update) throws TelegramApiException {
        String languageCode = userService.getLocaleForAnswerToUser(update);
        return tgmMessageService.createButtonMessage(
                userService.findUser(update).getChatId(),
                "-",
                getScheduleMenu(languageCode)
        );
    }

    @Override
    public SendMessage setExperienceSearchParameters(Update update) throws TelegramApiException {
        String languageCode = userService.getLocaleForAnswerToUser(update);

        String value = tgmLocaleElementService.getValueFromCallbackQueryData(update);

        searchParameters.put(SearchParameters.SearchParam.EXPERIENCE, value);

        return new SendMessage()
                .setChatId(userService.findUser(update).getChatId())
                .setText("helllllo");
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

    private InlineKeyboardMarkup getEmploymentMenu(String language) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.employment.full", language),
                        tgmLocaleElementService.createAutoCallbackButton("search.employment.part", language),
                        tgmLocaleElementService.createAutoCallbackButton("search.employment.project", language),
                        tgmLocaleElementService.createAutoCallbackButton("search.employment.volunteer", language),
                        tgmLocaleElementService.createAutoCallbackButton("search.employment.probation", language)
                )
        );
        return tgmElementService.createInlineKeyboardMarkup(rowsInline);
    }

    private InlineKeyboardMarkup getScheduleMenu(String language) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.schedule.fullDay", language),
                        tgmLocaleElementService.createAutoCallbackButton("search.schedule.shift", language),
                        tgmLocaleElementService.createAutoCallbackButton("search.schedule.flexible", language),
                        tgmLocaleElementService.createAutoCallbackButton("search.schedule.remote", language),
                        tgmLocaleElementService.createAutoCallbackButton("search.schedule.flyInFlyOut", language)
                )
        );
        return tgmElementService.createInlineKeyboardMarkup(rowsInline);
    }
}

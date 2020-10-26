package ru.hh.radar.controller;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.model.SearchParametersType;
import ru.hh.radar.model.entity.SearchParameters;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.service.common.SearchParametersService;
import ru.hh.radar.service.common.UserService;
import ru.hh.radar.service.telegram.SearchService;
import ru.hh.radar.telegram.annotations.BotController;
import ru.hh.radar.telegram.annotations.BotRequestMapping;
import ru.hh.radar.telegram.service.IncomingUpdateService;
import ru.hh.radar.telegram.service.InlineKeyboardService;
import ru.hh.radar.telegram.service.TelegramMessageService;

import java.util.List;

@BotController
@RequiredArgsConstructor
public class SearchController {

    private final IncomingUpdateService incomingUpdateService;
    private final TelegramMessageService tgmMessageService;
    private final InlineKeyboardService inlineKeyboardService;
    private final SearchParametersService searchParametersService;

    private final SearchService searchService;
    private final UserService userService;

    @BotRequestMapping("search.start")
    public SendMessage startSearch(Update update) throws TelegramApiException {
        String lang = incomingUpdateService.getLanguageCode(update);
        return tgmMessageService.createMenuMessage(
                "️✏️",
                inlineKeyboardService.getMainSearchMenu(lang)
        ).setChatId(incomingUpdateService.getChatId(update));
    }

    @BotRequestMapping("search.experience")
    public SendMessage showExperienceMenu(Update update) throws TelegramApiException {
        String lang = incomingUpdateService.getLanguageCode(update);
        return tgmMessageService
                .createButtonMessage(inlineKeyboardService.getExperienceMenu(lang))
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @BotRequestMapping(value = {
            "/search.experience.noExperience", "/search.experience.between1And3",
            "/search.experience.between3And6", "/search.experience.moreThan6"
    })
    public SendMessage setExperienceMenu(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserName(update));
        String value =  incomingUpdateService.getValueFromCallbackQuery(update);
        SearchParameters parameters = saveSearchParameters(SearchParametersType.EXPERIENCE, user, value);
        return new SendMessage()
                .setText(searchParametersService.toString(parameters, getLang(update)))
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @BotRequestMapping("search.employment")
    public SendMessage showEmploymentMenu(Update update) throws TelegramApiException {
        String lang = incomingUpdateService.getLanguageCode(update);
        return tgmMessageService
                .createButtonMessage(inlineKeyboardService.getEmploymentMenu(lang))
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @BotRequestMapping(value = {
            "/search.employment.full", "/search.employment.part", "/search.employment.project",
            "/search.employment.volunteer", "/search.employment.probation"
    })
    public SendMessage setEmploymentMenu(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserName(update));
        String value =  incomingUpdateService.getValueFromCallbackQuery(update);
        SearchParameters parameters = saveSearchParameters(SearchParametersType.EMPLOYMENT, user, value);
        return new SendMessage()
                .setText(searchParametersService.toString(parameters, getLang(update)))
                .setChatId(incomingUpdateService.getChatId(update));
    }


    @BotRequestMapping("search.schedule")
    public SendMessage showScheduleMenu(Update update) throws TelegramApiException {
        String lang = incomingUpdateService.getLanguageCode(update);
        return tgmMessageService
                .createButtonMessage(inlineKeyboardService.getScheduleMenu(lang))
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @BotRequestMapping(value = {
            "/search.schedule.fullDay", "/search.schedule.shift", "/search.schedule.flexible",
            "/search.schedule.remote", "/search.schedule.flyInFlyOut"
    })
    public SendMessage setScheduleMenu(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserName(update));
        String value =  incomingUpdateService.getValueFromCallbackQuery(update);
        SearchParameters parameters = saveSearchParameters(SearchParametersType.SCHEDULE, user, value);
        return new SendMessage()
                .setText(searchParametersService.toString(parameters, getLang(update)))
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @BotRequestMapping("search.area")
    public SendMessage showAreaMenu(Update update) throws TelegramApiException {
        String lang = incomingUpdateService.getLanguageCode(update);
        return tgmMessageService
                .createButtonMessage(inlineKeyboardService.getAreaMenu(lang))
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @BotRequestMapping(value = {
            "/search.area.1", "/search.area.113", "/search.area.1438",
            "/search.area.88", "/search.area.1202", "/search.area.other"
    })
    public SendMessage setAreaMenu(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserName(update));
        String value =  incomingUpdateService.getValueFromCallbackQuery(update);
        SearchParameters parameters = saveSearchParameters(SearchParametersType.AREA, user, value);
        return new SendMessage()
                .setText(searchParametersService.toString(parameters, getLang(update)))
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @BotRequestMapping("search.specialization")
    public SendMessage showSpecializationMenu(Update update) throws TelegramApiException {
        String lang = incomingUpdateService.getLanguageCode(update);
        return tgmMessageService
                .createButtonMessage(inlineKeyboardService.getSpecializationMenu(lang))
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @BotRequestMapping(value = {
            "/search.specialization.1", "/search.specialization.2", "/search.specialization.3", "/search.specialization.4",
            "/search.specialization.5", "/search.specialization.6", "/search.specialization.7", "/search.specialization.8",
            "/search.specialization.9", "/search.specialization.10", "/search.specialization.11", "/search.specialization.12",
            "/search.specialization.13", "/search.specialization.14", "/search.specialization.15", "/search.specialization.16",
            "/search.specialization.17", "/search.specialization.18", "/search.specialization.19", "/search.specialization.20",
            "/search.specialization.21", "/search.specialization.22", "/search.specialization.23", "/search.specialization.24",
            "/search.specialization.25", "/search.specialization.27", "/search.specialization.29"
    })
    public SendMessage setSpecializationMenu(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserName(update));
        String value =  incomingUpdateService.getValueFromCallbackQuery(update);
        SearchParameters parameters = saveSearchParameters(SearchParametersType.SPECIALIZATION, user, value);

        if(value.equals("1")) return showItSpecializationMenu(update);
        return new SendMessage()
                .setText(searchParametersService.toString(parameters, getLang(update)))
                .setChatId(incomingUpdateService.getChatId(update));
    }

    public SendMessage showItSpecializationMenu(Update update) throws TelegramApiException {
        return tgmMessageService
                .createButtonMessage(inlineKeyboardService.getItSpecializationMenu(getLang(update)))
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @BotRequestMapping(value = {
            "/search.specialization.1.3", "/search.specialization.1.9", "/search.specialization.1.25", "/search.specialization.1.82",
            "/search.specialization.1.110", "/search.specialization.1.113", "/search.specialization.1.117", "/search.specialization.1.137",
            "/search.specialization.1.172", "/search.specialization.1.211", "/search.specialization.1.221", "/search.specialization.1.270",
            "/search.specialization.1.273", "/search.specialization.1.327", "/search.specialization.1.400",
            "/search.specialization.1.420", "/search.specialization.1.474", "/search.specialization.1.475"
    })
    public SendMessage setItSpecializationMenu(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserName(update));
        String value =  incomingUpdateService.getLongValueFromCallbackQuery(update);
        SearchParameters parameters =  saveSearchParameters(SearchParametersType.SPECIALIZATION, user, value);
        return new SendMessage()
                .setText(searchParametersService.toString(parameters, getLang(update)))
                .setChatId(incomingUpdateService.getChatId(update));

    }

    @BotRequestMapping("search.run")
    public List<SendMessage> runSearch(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserName(update));
        String lang = incomingUpdateService.getLanguageCode(update);
        List<SendMessage> messages = searchService.runSearch(user.getSearchParameters(), lang);
        messages.forEach(sendMessage -> {
            try {
                sendMessage.setChatId(incomingUpdateService.getChatId(update));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        });
        return messages;
    }

    @BotRequestMapping("search.clean")
    public SendMessage cleanSearch(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserName(update));
        user = userService.cleanSearchParameters(user);
        return new SendMessage()
                .setText(searchParametersService.toString(user.getSearchParameters(), getLang(update)))
                .setChatId(incomingUpdateService.getChatId(update));
    }

    private SearchParameters saveSearchParameters(SearchParametersType parameter, User user, String value) {
        return userService
                .saveSearchParameter(user, parameter, (value.equals("other")) ? null : value)
                .getSearchParameters();
    }

    private String getLang(Update update) throws TelegramApiException {
        return incomingUpdateService.getLanguageCode(update);
    }
}

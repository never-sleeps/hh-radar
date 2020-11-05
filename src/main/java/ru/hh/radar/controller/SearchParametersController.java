package ru.hh.radar.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.model.SearchParametersType;
import ru.hh.radar.model.entity.SearchParameters;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.service.common.SearchParametersService;
import ru.hh.radar.service.common.UserService;
import ru.hh.radar.telegram.annotations.BotController;
import ru.hh.radar.telegram.annotations.BotRequestMapping;
import ru.hh.radar.telegram.service.IncomingUpdateService;
import ru.hh.radar.utils.Utils;

@BotController
@Api("Сервис сохранения параметров поиска вакансий")
@RequiredArgsConstructor
public class SearchParametersController {

    private final IncomingUpdateService incomingUpdateService;
    private final SearchParametersService searchParametersService;

    private final UserService userService;

    @ApiOperation("Сохранение параметра поиска вакансий 'Опыт работы'")
    @BotRequestMapping("/search.experience")
    public SendMessage setExperienceMenu(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserName(update));
        String value = Utils.getCommandValue(incomingUpdateService.getCommand(update));
        SearchParameters parameters = saveSearchParameters(SearchParametersType.EXPERIENCE, user, value);
        return new SendMessage()
                .setText(searchParametersService.toString(parameters, getLang(update)))
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @ApiOperation("Сохранение параметра поиска вакансий 'Тип занятости'")
    @BotRequestMapping("/search.employment")
    public SendMessage setEmploymentMenu(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserName(update));
        String value =  Utils.getCommandValue(incomingUpdateService.getCommand(update));
        SearchParameters parameters = saveSearchParameters(SearchParametersType.EMPLOYMENT, user, value);
        return new SendMessage()
                .setText(searchParametersService.toString(parameters, getLang(update)))
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @ApiOperation("Сохранение параметра поиска вакансий 'График работы'")
    @BotRequestMapping("/search.schedule")
    public SendMessage setScheduleMenu(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserName(update));
        String value =  Utils.getCommandValue(incomingUpdateService.getCommand(update));
        SearchParameters parameters = saveSearchParameters(SearchParametersType.SCHEDULE, user, value);
        return new SendMessage()
                .setText(searchParametersService.toString(parameters, getLang(update)))
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @ApiOperation("Сохранение параметра поиска вакансий 'Регион'")
    @BotRequestMapping("/search.area")
    public SendMessage setAreaMenu(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserName(update));
        String value = Utils.getCommandValue(incomingUpdateService.getCommand(update));
        SearchParameters parameters = saveSearchParameters(SearchParametersType.AREA, user, value);
        return new SendMessage()
                .setText(searchParametersService.toString(parameters, getLang(update)))
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @ApiOperation("Сохранение параметра поиска вакансий 'Профобласть'")
    @BotRequestMapping("/search.specialization")
    public SendMessage setSpecializationMenu(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserName(update));
        String value = Utils.getCommandValue(incomingUpdateService.getCommand(update));
        SearchParameters parameters = saveSearchParameters(SearchParametersType.SPECIALIZATION, user, value);

        return new SendMessage()
                .setText(searchParametersService.toString(parameters, getLang(update)))
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @ApiOperation("Сохранение параметра поиска вакансий 'Должность'")
    @BotRequestMapping("/search_position")
    public SendMessage setPositionValue(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserName(update));
        String value = Utils.getCommandValue(incomingUpdateService.getCommand(update));
        SearchParameters parameters = saveSearchParameters(SearchParametersType.TEXT, user, value);

        return new SendMessage()
                .setText(searchParametersService.toString(parameters, getLang(update)))
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @ApiOperation("Очистка сохранённых параметра поиска вакансий")
    @BotRequestMapping(value = "search.clean", isLocale = true)
    public SendMessage cleanSearchParameters(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserName(update));
        user = userService.cleanSearchParameters(user);
        return new SendMessage()
                .setText(searchParametersService.toString(user.getSearchParameters(), getLang(update)))
                .setChatId(incomingUpdateService.getChatId(update));
    }

    private SearchParameters saveSearchParameters(SearchParametersType parameter, User user, String value) {
        return userService
                .saveSearchParameter(user, parameter, value)
                .getSearchParameters();
    }

    private String getLang(Update update) {
        return incomingUpdateService.getLanguageCode(update);
    }
}

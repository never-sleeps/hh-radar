package ru.hh.radar.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.dto.ResumeDTO;
import ru.hh.radar.dto.VacancyDTO;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.service.common.SearchParametersService;
import ru.hh.radar.service.common.UserService;
import ru.hh.radar.service.hh.HhResumeService;
import ru.hh.radar.service.hh.HhVacancyService;
import ru.hh.radar.telegram.annotations.BotController;
import ru.hh.radar.telegram.annotations.BotRequestMapping;
import ru.hh.radar.telegram.service.IncomingUpdateService;
import ru.hh.radar.telegram.service.TelegramMessageService;
import ru.hh.radar.utils.Utils;

import java.util.List;

@BotController
@Api("Сервис поиска вакансий")
@RequiredArgsConstructor
public class SearchController {

    private final IncomingUpdateService incomingUpdateService;
    private final TelegramMessageService tgmMessageService;
    private final UserService userService;
    private final SearchParametersService searchParametersService;
    private final HhVacancyService hhVacancyService;
    private final HhResumeService hhResumeService;

    @ApiOperation("Поиск по вакансиям. Отображение результатов по 3 вакансии в блоке")
    @BotRequestMapping(value = "search.run", isLocale = true)
    public List<SendMessage> runSearchVacancies(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserName(update));
        searchParametersService.resetSearchPage(user.getSearchParameters());
        return executeSearch(update);
    }

    @ApiOperation("Отображение следующего блока вакансий")
    @BotRequestMapping("/next.search.page")
    public List<SendMessage> runSearchVacanciesNext(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserName(update));
        searchParametersService.incrementSearchPage(user.getSearchParameters());
        return executeSearch(update);
    }

    @ApiOperation("Поиск по вакансиям, похожим на резюме. Отображение результатов по 3 вакансии в блоке")
    @BotRequestMapping(value = "/search.similar.run")
    public List<SendMessage> runSearchSimilarVacancies(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserName(update));
        searchParametersService.resetSearchPage(user.getSearchParameters());
        return executeSimilarSearch(update);
    }

    @ApiOperation("Поиск по вакансиям, похожим на резюме. Отображение результатов по 3 вакансии в блоке")
    @BotRequestMapping(value = "/next.search.similar.run")
    public List<SendMessage> runSearchSimilarVacanciesNext(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserName(update));
        searchParametersService.incrementSearchPage(user.getSearchParameters());
        return executeSimilarSearch(update);
    }

    private List<SendMessage> executeSimilarSearch(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserName(update));
        String resumeId = Utils.getCommandValue(incomingUpdateService.getCommand(update));
        Long chatId = incomingUpdateService.getChatId(update);
        String lang = incomingUpdateService.getLanguageCode(update);
        ResumeDTO resume = hhResumeService.getResume(resumeId, user);

        List<VacancyDTO> vacancies = hhVacancyService.getSimilarVacancies(resume, user);
        String nextPageCommand = "/next.search.similar.run" + " " + resume.getId();
        return tgmMessageService.createVacancyMessages(vacancies, nextPageCommand, chatId, lang);
    }

    private List<SendMessage> executeSearch(Update update) throws TelegramApiException {
        Long chatId = incomingUpdateService.getChatId(update);
        String lang = incomingUpdateService.getLanguageCode(update);
        User user = userService.findUser(incomingUpdateService.getUserName(update));

        List<VacancyDTO> vacancies = hhVacancyService.getVacancies(user.getSearchParameters());
        String nextPageCommand = "/next.search.page";
        return tgmMessageService.createVacancyMessages(vacancies, nextPageCommand, chatId, lang);
    }
}

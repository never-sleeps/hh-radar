package ru.hh.radar.controller;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.dto.VacancyDTO;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.service.common.SearchParametersService;
import ru.hh.radar.service.common.UserService;
import ru.hh.radar.service.hh.HhVacancyService;
import ru.hh.radar.telegram.annotations.BotController;
import ru.hh.radar.telegram.annotations.BotRequestMapping;
import ru.hh.radar.telegram.service.IncomingUpdateService;
import ru.hh.radar.telegram.service.TelegramMessageService;

import java.util.List;

@BotController
@RequiredArgsConstructor
public class SearchController {

    private final IncomingUpdateService incomingUpdateService;
    private final TelegramMessageService tgmMessageService;

    private final UserService userService;
    private final SearchParametersService searchParametersService;
    private final HhVacancyService hhVacancyService;

    @BotRequestMapping("search.run")
    public List<SendMessage> runSearch(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserName(update));
        searchParametersService.resetSearchPage(user.getSearchParameters());

        Long chatId = incomingUpdateService.getChatId(update);
        String lang = incomingUpdateService.getLanguageCode(update);
        return getVacansiesMessages(
                userService.findUser(incomingUpdateService.getUserName(update)),
                chatId,
                lang
        );
    }

    @BotRequestMapping("/nextsearchpage")
    public List<SendMessage> showNextSearchPage(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserName(update));
        searchParametersService.incrementSearchPage(user.getSearchParameters());

        Long chatId = incomingUpdateService.getChatId(update);
        String lang = incomingUpdateService.getLanguageCode(update);
        return getVacansiesMessages(
                userService.findUser(incomingUpdateService.getUserName(update)),
                chatId,
                lang
        );
    }

    private List<SendMessage> getVacansiesMessages(User user, Long chatId, String lang) {
        List<VacancyDTO> vacancies = hhVacancyService.getVacancies(user.getSearchParameters()).getItems();
        return tgmMessageService.createVacancyMessages(vacancies, chatId, lang);
    }
}

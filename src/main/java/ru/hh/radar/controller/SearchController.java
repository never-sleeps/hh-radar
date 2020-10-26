package ru.hh.radar.controller;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.dto.VacancyDTO;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.service.common.UserService;
import ru.hh.radar.service.hh.HhVacancyService;
import ru.hh.radar.telegram.annotations.BotController;
import ru.hh.radar.telegram.annotations.BotRequestMapping;
import ru.hh.radar.telegram.service.IncomingUpdateService;
import ru.hh.radar.telegram.service.MessageService;
import ru.hh.radar.telegram.service.TelegramMessageService;

import java.util.List;

@BotController
@RequiredArgsConstructor
public class SearchController {

    private final IncomingUpdateService incomingUpdateService;
    private final TelegramMessageService tgmMessageService;
    private final MessageService messageService;

    private final UserService userService;
    private final HhVacancyService hhVacancyService;

    @BotRequestMapping("search.run")
    public List<SendMessage> runSearch(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserName(update));
        String lang = incomingUpdateService.getLanguageCode(update);

        String linkText = messageService.getMessage("browser.open", lang);
        List<VacancyDTO> vacancies = hhVacancyService.getVacancies(user.getSearchParameters()).getItems();
        Long chatId = incomingUpdateService.getChatId(update);
        return tgmMessageService.createVacancyMessages(
                linkText, vacancies, chatId
        );
    }
}

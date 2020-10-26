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

    private final SearchService searchService;
    private final UserService userService;

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
}

package ru.hh.radar.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.service.common.UserService;
import ru.hh.radar.service.hh.HhOauthService;
import ru.hh.radar.service.telegram.AuthorizeService;
import ru.hh.radar.service.telegram.StartService;
import ru.hh.radar.telegram.annotations.BotController;
import ru.hh.radar.telegram.annotations.BotRequestMapping;
import ru.hh.radar.telegram.service.IncomingUpdateService;

@Slf4j
@BotController
@RequiredArgsConstructor
public class StartController {

    private final StartService startService;
    private final AuthorizeService authorizeService;
    private final HhOauthService hhOauthService;

    private final UserService userService;
    private final IncomingUpdateService incomingUpdateService;

    @BotRequestMapping("/start")
    public SendMessage start(Update update) throws TelegramApiException {
        String userName = incomingUpdateService.getMessage(update).getFrom().getUserName();
        String command = incomingUpdateService.getCommand(update);

        User user = hhOauthService.authorizeUser(userName, command);
        String lang = incomingUpdateService.getLanguageCode(update);

        return startService.showStartMenu(user, lang)
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @BotRequestMapping("authorize.user")
    public SendMessage authorizeUser(Update update) throws TelegramApiException {
        User user = userService.findUser(
                incomingUpdateService.getUserName(update)
        );
        String lang = incomingUpdateService.getLanguageCode(update);
        return authorizeService.showAuthorizeButton(user, lang)
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @BotRequestMapping("mainmenu")
    public SendMessage showStartMenu(Update update) throws TelegramApiException {
        User user = userService.findUser(
                incomingUpdateService.getUserName(update)
        );
        String lang = incomingUpdateService.getLanguageCode(update);
        return startService.showStartMenu(user, lang)
                .setChatId(incomingUpdateService.getChatId(update));
    }
}

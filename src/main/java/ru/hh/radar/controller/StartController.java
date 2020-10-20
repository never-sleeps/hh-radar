package ru.hh.radar.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.service.hh.HhOauthService;
import ru.hh.radar.service.hh.HhUserService;
import ru.hh.radar.service.telegram.AuthorizeService;
import ru.hh.radar.service.telegram.StartService;
import ru.hh.radar.service.common.UserService;
import ru.hh.radar.telegram.annotations.BotController;
import ru.hh.radar.telegram.annotations.BotRequestMapping;
import ru.hh.radar.telegram.service.TelegramService;

@Slf4j
@BotController
@RequiredArgsConstructor
public class StartController {

    private final StartService startService;
    private final AuthorizeService authorizeService;
    private final HhOauthService hhOauthService;

    @BotRequestMapping("/start")
    public SendMessage start(Update update) throws TelegramApiException {
        hhOauthService.authorizeUser(update);
        return startService.showStartMenu(update);
    }

    @BotRequestMapping("authorize.user")
    public SendMessage authorizeUser(Update update) throws TelegramApiException {
        return authorizeService.showAuthorizeButton(update);
    }

    @BotRequestMapping("cancel")
    public SendMessage showStartMenu(Update update) throws TelegramApiException {
        return startService.showStartMenu(update);
    }
}

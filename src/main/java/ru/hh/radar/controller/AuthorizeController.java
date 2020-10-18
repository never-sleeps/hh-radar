package ru.hh.radar.controller;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.service.telegram.AuthorizeService;
import ru.hh.radar.telegram.annotations.BotController;
import ru.hh.radar.telegram.annotations.BotRequestMapping;

@BotController
@RequiredArgsConstructor
public class AuthorizeController {

    private final AuthorizeService authorizeService;

    @BotRequestMapping("authorize.user")
    public SendMessage authorizeUser(Update update) throws TelegramApiException {
        return authorizeService.showAuthorizeButton(update);
    }
}

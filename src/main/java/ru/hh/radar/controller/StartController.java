package ru.hh.radar.controller;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.service.telegram.StartService;
import ru.hh.radar.service.common.UserService;
import ru.hh.radar.telegram.annotations.BotController;
import ru.hh.radar.telegram.annotations.BotRequestMapping;

@BotController
@RequiredArgsConstructor
public class StartController {

    private final UserService userService;
    private final StartService startService;

    @BotRequestMapping("/start")
    public SendMessage start(Update update) throws TelegramApiException {
        userService.createUser(update);
        return startService.showStartMenu(update);
    }

    @BotRequestMapping("cancel")
    public SendMessage showStartMenu(Update update) throws TelegramApiException {
        return startService.showStartMenu(update);
    }
}

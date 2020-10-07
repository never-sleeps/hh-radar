package ru.hh.radar.controller;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.model.User;
import ru.hh.radar.service.telegram.UserService;
import ru.hh.radar.telegram.annotations.BotController;
import ru.hh.radar.telegram.annotations.BotRequestMapping;

@BotController
@RequiredArgsConstructor
public class StartController {

    private final UserService userService;

    @BotRequestMapping("/start")
    public SendMessage start(Update update) throws TelegramApiException {
        User user = userService.createUser(update);
        return new SendMessage()
                .setChatId(user.getChatId())
                .setText("start");
    }
}

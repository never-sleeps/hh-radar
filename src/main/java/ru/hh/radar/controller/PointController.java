package ru.hh.radar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.telegram.annotations.BotController;
import ru.hh.radar.telegram.annotations.BotRequestMapping;
import ru.hh.radar.service.telegram.PointService;

@BotController
public class PointController {

    private final PointService pointService;

    @Autowired
    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    @BotRequestMapping("/text")
    public SendMessage showText(Update update) throws TelegramApiException {
        return pointService.showText(update);
    }


    @BotRequestMapping("/btn")
    public SendMessage showButton(Update update) throws TelegramApiException {
        return pointService.showButton(update);
    }

    @BotRequestMapping("/menu")
    public SendMessage showMenu(Update update) throws TelegramApiException {
        return pointService.showMenu(update);
    }

    @BotRequestMapping("/hh")
    public SendMessage showHh(Update update) throws TelegramApiException {
        return pointService.showHh(update);
    }
    @BotRequestMapping("/showCommand")
    public SendMessage showCommandText(Update update) throws TelegramApiException {
        return pointService.showCommandText(update);
    }
}

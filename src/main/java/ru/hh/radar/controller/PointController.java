package ru.hh.radar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
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

    @BotRequestMapping("/point")
    public SendMessage testPoint(Update update) {
        return pointService.testPoint(update);
    }


    @BotRequestMapping( "/test")
    public SendMessage test(Update update) {
        return pointService.test(update);
    }
}

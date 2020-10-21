package ru.hh.radar.controller;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.service.telegram.ResumeService;
import ru.hh.radar.telegram.annotations.BotController;
import ru.hh.radar.telegram.annotations.BotRequestMapping;

@BotController
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @BotRequestMapping("all.resume")
    public SendMessage showAllResume(Update update) throws TelegramApiException {
        return resumeService.showAllResume(update);
    }
}

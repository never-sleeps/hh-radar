package ru.hh.radar.controller;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.telegram.annotations.BotController;
import ru.hh.radar.telegram.annotations.BotRequestMapping;
import ru.hh.radar.telegram.service.IncomingUpdateService;
import ru.hh.radar.telegram.service.InlineKeyboardService;
import ru.hh.radar.telegram.service.TelegramMessageService;

@BotController
@RequiredArgsConstructor
public class SearchKeyboardController {

    private final IncomingUpdateService incomingUpdateService;
    private final TelegramMessageService tgmMessageService;
    private final InlineKeyboardService inlineKeyboardService;

    @BotRequestMapping("search.start")
    public SendMessage startSearch(Update update) throws TelegramApiException {
        String lang = incomingUpdateService.getLanguageCode(update);
        return tgmMessageService.createMenuMessage(
                "️✏️",
                inlineKeyboardService.getMainSearchMenu(lang)
        ).setChatId(incomingUpdateService.getChatId(update));
    }

    @BotRequestMapping("search.experience")
    public SendMessage showExperienceMenu(Update update) throws TelegramApiException {
        String lang = incomingUpdateService.getLanguageCode(update);
        return tgmMessageService
                .createButtonMessage(inlineKeyboardService.getExperienceMenu(lang))
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @BotRequestMapping("search.employment")
    public SendMessage showEmploymentMenu(Update update) throws TelegramApiException {
        String lang = incomingUpdateService.getLanguageCode(update);
        return tgmMessageService
                .createButtonMessage(inlineKeyboardService.getEmploymentMenu(lang))
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @BotRequestMapping("search.schedule")
    public SendMessage showScheduleMenu(Update update) throws TelegramApiException {
        String lang = incomingUpdateService.getLanguageCode(update);
        return tgmMessageService
                .createButtonMessage(inlineKeyboardService.getScheduleMenu(lang))
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @BotRequestMapping("search.area")
    public SendMessage showAreaMenu(Update update) throws TelegramApiException {
        String lang = incomingUpdateService.getLanguageCode(update);
        return tgmMessageService
                .createButtonMessage(inlineKeyboardService.getAreaMenu(lang))
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @BotRequestMapping("search.specialization")
    public SendMessage showSpecializationMenu(Update update) throws TelegramApiException {
        String lang = incomingUpdateService.getLanguageCode(update);
        return tgmMessageService
                .createButtonMessage(inlineKeyboardService.getSpecializationMenu(lang))
                .setChatId(incomingUpdateService.getChatId(update));
    }
}

package ru.hh.radar.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.dto.ResumeDTO;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.service.common.AutoPublishingResumeService;
import ru.hh.radar.service.common.UserService;
import ru.hh.radar.service.hh.HhResumeService;
import ru.hh.radar.telegram.annotations.BotController;
import ru.hh.radar.telegram.annotations.BotRequestMapping;
import ru.hh.radar.telegram.service.*;
import ru.hh.radar.utils.Utils;

import java.util.List;

@Slf4j
@BotController
@Api("Сервис автообновления резюме пользователя")
@RequiredArgsConstructor
public class AutoPublishResumeController {

    private final IncomingUpdateService incomingUpdateService;
    private final TelegramMessageService telegramMessageService;
    private final TelegramElementService telegramElementService;
    private final InlineKeyboardService inlineKeyboardService;
    private final MessageService msg;
    private final UserService userService;
    private final AutoPublishingResumeService autoPublishingResumeService;
    private final HhResumeService hhResumeService;

    @ApiOperation("Резюме не доступно для автообновления")
    @BotRequestMapping("/auto.publish.notavailable")
    public SendMessage showNotAvailableAutoPublishmessage(Update update) throws TelegramApiException {
        return telegramMessageService.createMessage(msg.getMessage("resume.publish.warn1", getLang(update)))
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @ApiOperation("Включение/выключение автообновления для резюме")
    @BotRequestMapping("/auto.publish")
    public EditMessageReplyMarkup setAutoPublishingValue(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserName(update));
        String resumeId = Utils.getCommandValue(incomingUpdateService.getCommand(update));
        ResumeDTO resume = hhResumeService.getResume(resumeId, user);
        if (autoPublishingResumeService.isAutoPublishingResume(resumeId)) {
            autoPublishingResumeService.disableAutoPublishing(resume, user);
        } else {
            autoPublishingResumeService.enableAutoPublishing(resume, user);
        }

        List<ResumeDTO> items = hhResumeService.getAllResume(user);
        return telegramElementService.editMessageReplyMarkup(
                incomingUpdateService.getMessageId(update),
                inlineKeyboardService.getAutoPublishResumeMenu(getLang(update), items)
        ).setChatId(incomingUpdateService.getChatId(update));
    }

    @ApiOperation("Отображения меню настройки автообновлений резюме")
    @BotRequestMapping(value = "resume.publish.auto", isLocale = true)
    public SendMessage showAutoPublishResumeMenu(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserName(update));

        List<ResumeDTO> items = hhResumeService.getAllResume(user);
        return telegramMessageService.createButtonMessage(
                msg.getMessage("message.publish.auto.context", getLang(update)),
                inlineKeyboardService.getAutoPublishResumeMenu(getLang(update), items)
        ).setChatId(incomingUpdateService.getChatId(update));
    }

    private String getLang(Update update) {
        return incomingUpdateService.getLanguageCode(update);
    }
}

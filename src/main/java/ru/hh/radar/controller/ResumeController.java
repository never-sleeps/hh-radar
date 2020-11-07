package ru.hh.radar.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.dto.ResumeDTO;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.service.common.UserService;
import ru.hh.radar.service.hh.HhResumeService;
import ru.hh.radar.telegram.annotations.BotController;
import ru.hh.radar.telegram.annotations.BotRequestMapping;
import ru.hh.radar.telegram.service.IncomingUpdateService;
import ru.hh.radar.telegram.service.InlineKeyboardService;
import ru.hh.radar.telegram.service.MessageService;
import ru.hh.radar.telegram.service.TelegramMessageService;
import ru.hh.radar.utils.Utils;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@BotController
@Api("Сервис работы с резюме пользователя")
@RequiredArgsConstructor
public class ResumeController {

    private final IncomingUpdateService incomingUpdateService;
    private final TelegramMessageService telegramMessageService;
    private final InlineKeyboardService inlineKeyboardService;
    private final MessageService msg;
    private final UserService userService;
    private final HhResumeService hhResumeService;

    @Value("${headhunter.timeBetweenPublishing.hours}")
    private int timeBetweenPublishingInHours;

    @ApiOperation("Отображение списка всех резюме пользователя")
    @BotRequestMapping(value = "resume.all", isLocale = true)
    public List<SendMessage> showAllResume(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserId(update));

        if(!user.isAuthorized()) {
            log.warn(String.format("%s: getting the resume list. Cause: user is not authorized", user.getUsername()));
            return List.of(getSendMessageForUnauthorizedUser(getLang(update))
                    .setChatId(incomingUpdateService.getChatId(update)));
        }
        List<ResumeDTO> resumeList = hhResumeService.getAllResume(user);
        Long chatId = incomingUpdateService.getChatId(update);
        return telegramMessageService.createResumeInfoMessages(resumeList, chatId, getLang(update));

    }

    @ApiOperation("Отображение краткого списка всех резюме пользователя")
    @BotRequestMapping(value = "resume.publish", isLocale = true)
    public SendMessage showPublishResumeMenu(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserId(update));

        if(!user.isAuthorized()) {
            log.warn(String.format("%s: error getting the resume list. Cause: user is not authorized", user.getUsername()));
            return getSendMessageForUnauthorizedUser(getLang(update))
                    .setChatId(incomingUpdateService.getChatId(update));
        }
        List<ResumeDTO> items = hhResumeService.getAllResume(user);
        return telegramMessageService.createButtonMessage(
                msg.getMessage("message.publish.context", getLang(update)),
                inlineKeyboardService.getPublishResumeMenu(getLang(update), items)
        ).setChatId(incomingUpdateService.getChatId(update));
    }

    @ApiOperation("Публикация резюме пользователя")
    @BotRequestMapping("/publish")
    public SendMessage publishResume(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserId(update));
        String resumeId = Utils.getCommandValue(incomingUpdateService.getCommand(update));
        ResumeDTO resume = hhResumeService.getResume(resumeId, user);

        if(!resume.isPublished()) {
            return getSendMessageForNotPublishedResume(getLang(update))
                    .setChatId(incomingUpdateService.getChatId(update));
        }
        if(!resume.isCanBeUpdatedByTimePeriod(timeBetweenPublishingInHours)) {
            return getSendMessageForRecentlyUpdatedResume(resume, getLang(update))
                    .setChatId(incomingUpdateService.getChatId(update));
        }

        boolean isSuccessfullyPublished = hhResumeService.publishResume(resumeId, user);
        if(isSuccessfullyPublished) {
            return getSendMessageForSuccessfullyUpdatedResume(resume, getLang(update))
                    .setChatId(incomingUpdateService.getChatId(update));
        }
        return telegramMessageService.createMessage(msg.getMessage("message.error", getLang(update)));
    }

    private SendMessage getSendMessageForUnauthorizedUser(String lang) {
        return telegramMessageService.createMessage(msg.getMessage("message.not.authorized", lang));
    }

    private SendMessage getSendMessageForRecentlyUpdatedResume(ResumeDTO resume, String lang) {
        LocalDateTime newDate = resume.getUpdatedAt().plusHours(timeBetweenPublishingInHours);
        String messageText = msg.getMessage("resume.publish.warn2", lang) + " " + Utils.getFormattingData(newDate);
        return telegramMessageService.createMessage(messageText);
    }

    private SendMessage getSendMessageForNotPublishedResume(String lang) {
        return telegramMessageService
                .createMessage(msg.getMessage("resume.publish.warn1", lang));
    }

    private SendMessage getSendMessageForSuccessfullyUpdatedResume(ResumeDTO resume, String lang) {
        String messageText = msg.getMessage("resume.publish.ok", lang) + ": " + resume.getTitle();
        return telegramMessageService.createMessage(messageText);
    }

    private String getLang(Update update) {
        return incomingUpdateService.getLanguageCode(update);
    }
}

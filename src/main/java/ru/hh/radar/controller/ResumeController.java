package ru.hh.radar.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.dto.ResumeDTO;
import ru.hh.radar.dto.ResumeStatusDTO;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.service.Utils;
import ru.hh.radar.service.common.UserService;
import ru.hh.radar.service.hh.HhResumeService;
import ru.hh.radar.telegram.annotations.BotController;
import ru.hh.radar.telegram.annotations.BotRequestMapping;
import ru.hh.radar.telegram.service.IncomingUpdateService;
import ru.hh.radar.telegram.service.InlineKeyboardService;
import ru.hh.radar.telegram.service.MessageService;
import ru.hh.radar.telegram.service.TelegramMessageService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@BotController
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

    @BotRequestMapping("resume.all")
    public List<SendMessage> showAllResume(Update update) throws TelegramApiException {
        String lang = incomingUpdateService.getLanguageCode(update);
        User user = userService.findUser(incomingUpdateService.getUserName(update));

        if(!user.isAuthorized()) {
            log.warn(String.format("%s: getting the resume list. Cause: user is not authorized", user.getUsername()));
            return List.of(getSendMessageForUnauthorizedUser(lang)
                    .setChatId(incomingUpdateService.getChatId(update)));
        }
        Map<ResumeDTO, ResumeStatusDTO> resumeMap = hhResumeService.getAllResumeInfo(user);
        Long chatId = incomingUpdateService.getChatId(update);
        return telegramMessageService.createResumeMessages(lang, resumeMap, chatId);

    }

    @BotRequestMapping("resume.publish")
    public SendMessage showPublishResumeMenu(Update update) throws TelegramApiException {
        String lang = incomingUpdateService.getLanguageCode(update);
        User user = userService.findUser(incomingUpdateService.getUserName(update));

        if(!user.isAuthorized()) {
            log.warn(String.format("%s: error getting the resume list. Cause: user is not authorized", user.getUsername()));
            return getSendMessageForUnauthorizedUser(lang)
                    .setChatId(incomingUpdateService.getChatId(update));
        }
        List<ResumeDTO> items = hhResumeService.getAllResume(user).getItems();
        return telegramMessageService.createButtonMessage(
                msg.getMessage("message.publish.context", lang),
                inlineKeyboardService.getResumeMenu(lang, items)
        ).setChatId(incomingUpdateService.getChatId(update));
    }

    @BotRequestMapping("/publish")
    public SendMessage publishResume(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserName(update));
        String resumeId = Utils.getCommandValue(incomingUpdateService.getCommand(update));
        String lang = incomingUpdateService.getLanguageCode(update);
        ResumeDTO resume = hhResumeService.getResume(resumeId, user);

        if(!resume.isPublished()) {
            return telegramMessageService.createMessage(msg.getMessage("resume.publish.warn1", lang))
                    .setChatId(incomingUpdateService.getChatId(update));
        }
        if(!resume.isCanBeUpdatedByTimePeriod(timeBetweenPublishingInHours)) {
            LocalDateTime newDate = resume.getUpdatedAt().plusHours(timeBetweenPublishingInHours);
            return telegramMessageService.createMessage(
                    msg.getMessage("resume.publish.warn2", lang) + " " + Utils.getFormattingData(newDate)
            ).setChatId(incomingUpdateService.getChatId(update));
        }

        boolean isPublished = hhResumeService.publishResume(resumeId, user);
        if(isPublished) {
            return telegramMessageService.createMessage(
                    msg.getMessage("resume.publish.ok", lang) + ": " + resume.getTitle()
            ).setChatId(incomingUpdateService.getChatId(update));
        }
        return telegramMessageService.createMessage(msg.getMessage("message.error", lang));
    }


    private SendMessage getSendMessageForUnauthorizedUser(String lang) {
        return telegramMessageService.createMessage(msg.getMessage("message.not.authorized", lang));
    }
}

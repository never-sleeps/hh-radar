package ru.hh.radar.service.telegram.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.dto.ResumeDTO;
import ru.hh.radar.dto.ResumeStatusDTO;
import ru.hh.radar.dto.ResumesResultsDTO;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.service.Utils;
import ru.hh.radar.service.common.UserService;
import ru.hh.radar.service.hh.HhResumeService;
import ru.hh.radar.service.telegram.InlineKeyboardService;
import ru.hh.radar.service.telegram.ResumeService;
import ru.hh.radar.telegram.service.MessageService;
import ru.hh.radar.telegram.service.TelegramMessageService;
import ru.hh.radar.telegram.service.TelegramService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class ResumeServiceImpl implements ResumeService {

    private final InlineKeyboardService inlineKeyboardService;
    private final TelegramMessageService tgmMessageService;
    private final TelegramService telegramService;

    private final MessageService msg;
    private final UserService userService;
    private final HhResumeService hhResumeService;

    @Value("${headhunter.timeBetweenPublishing.hours}")
    private int timeBetweenPublishingInHours;

    public List<SendMessage> showAllResume(Update update) throws TelegramApiException {
        String lang = userService.getLanguageCode(update);
        User user = userService.findUser(update);

        if(!user.isAuthorized()) {
            log.warn(String.format("%s: error publishing resume. Cause: user is not authorized", user.getUsername()));
            return List.of(getSendMessageForUnauthorizedUser(user.getChatId(), lang));
        }
        Map<ResumeDTO, ResumeStatusDTO> resumeMap = new HashMap<>();
        for (ResumeDTO resume: hhResumeService.getAllResume(user).getItems()) {
            resumeMap.put(resume, hhResumeService.getStatusResume(resume.getId(), user));
        }
        return tgmMessageService.createResumeMessages(user.getChatId(), lang, resumeMap);
    }

    @Override
    public SendMessage showPublishResumeMenu(Update update) throws TelegramApiException {
        String lang = userService.getLanguageCode(update);
        User user = userService.findUser(update);

        if(!user.isAuthorized()) {
            log.warn(String.format("%s: error getting the resume list. Cause: user is not authorized", user.getUsername()));
            return getSendMessageForUnauthorizedUser(user.getChatId(), lang);
        }
        ResumesResultsDTO results = hhResumeService.getAllResume(user);
        return tgmMessageService.createButtonMessage(
                user.getChatId(),
                msg.getMessage("message.publish.context", lang),
                inlineKeyboardService.getResumeMenu(lang, results.getItems())
        );
    }

    @Override
    public SendMessage publishResume(Update update) throws TelegramApiException {
        String lang = userService.getLanguageCode(update);
        User user = userService.findUser(update);
        String resumeId = Utils.getCommandValue(telegramService.getCommand(update));

        ResumeDTO resume = hhResumeService.getResume(resumeId, user);

        if(!resume.isPublished()) {
            return getSendMessageForNotPublishedResume(user.getChatId(), lang);
        }
        if(!resume.isCanBeUpdatedByTimePeriod(timeBetweenPublishingInHours)) {
            LocalDateTime newDate = resume.getUpdatedAt().plusHours(timeBetweenPublishingInHours);
            return getSendMessageForCanNotBeUpdatedByTimePeriod(user.getChatId(), lang, newDate);
        }

        boolean isPublished = hhResumeService.publishResume(resumeId, user);
        if(isPublished) {
            String text = msg.getMessage("resume.publish.ok", lang) + ": " + resume.getTitle();
            return new SendMessage().
                    setChatId(user.getChatId())
                    .setText(text);
        }
        return getSendMessageForErrorPublished(user.getChatId(), lang);

    }

    private SendMessage getSendMessageForUnauthorizedUser(Long chatId, String lang) {
        return new SendMessage()
                .setChatId(chatId)
                .setText(msg.getMessage("message.not.authorized", lang));
    }

    private SendMessage getSendMessageForNotPublishedResume(Long chatId, String lang) {
        return new SendMessage()
                .setChatId(chatId)
                .setText(msg.getMessage("resume.publish.warn1", lang));
    }

    private SendMessage getSendMessageForCanNotBeUpdatedByTimePeriod(Long chatId, String lang, LocalDateTime newDate) {
        return new SendMessage()
                .setChatId(chatId)
                .setText(
                        msg.getMessage("resume.publish.warn2", lang) + " " + Utils.getFormattingData(newDate)
                );
    }

    private SendMessage getSendMessageForErrorPublished(Long chatId, String lang) {
        return new SendMessage()
                .setChatId(chatId)
                .setText(msg.getMessage("message.error", lang));
    }
}

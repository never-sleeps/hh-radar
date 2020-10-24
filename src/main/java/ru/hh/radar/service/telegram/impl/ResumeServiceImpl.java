package ru.hh.radar.service.telegram.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.hh.radar.dto.ResumeDTO;
import ru.hh.radar.dto.ResumeStatusDTO;
import ru.hh.radar.dto.ResumesResultsDTO;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.service.Utils;
import ru.hh.radar.service.hh.HhResumeService;
import ru.hh.radar.service.telegram.ResumeService;
import ru.hh.radar.telegram.service.InlineKeyboardService;
import ru.hh.radar.telegram.service.MessageService;
import ru.hh.radar.telegram.service.TelegramMessageService;

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

    private final MessageService msg;
    private final HhResumeService hhResumeService;

    @Value("${headhunter.timeBetweenPublishing.hours}")
    private int timeBetweenPublishingInHours;

    public List<SendMessage> showAllResume(User user, String lang) {

        if(!user.isAuthorized()) {
            log.warn(String.format("%s: error publishing resume. Cause: user is not authorized", user.getUsername()));
            return List.of(getSendMessageForUnauthorizedUser(lang));
        }
        Map<ResumeDTO, ResumeStatusDTO> resumeMap = new HashMap<>();
        for (ResumeDTO resume: hhResumeService.getAllResume(user).getItems()) {
            resumeMap.put(resume, hhResumeService.getStatusResume(resume.getId(), user));
        }
        return tgmMessageService.createResumeMessages(lang, resumeMap);
    }

    @Override
    public SendMessage showPublishResumeMenu(User user, String lang) {

        if(!user.isAuthorized()) {
            log.warn(String.format("%s: error getting the resume list. Cause: user is not authorized", user.getUsername()));
            return getSendMessageForUnauthorizedUser(lang);
        }
        ResumesResultsDTO results = hhResumeService.getAllResume(user);
        return tgmMessageService.createButtonMessage(
                msg.getMessage("message.publish.context", lang),
                inlineKeyboardService.getResumeMenu(lang, results.getItems())
        );
    }

    @Override
    public SendMessage publishResume(User user, String resumeId, String lang) {
        ResumeDTO resume = hhResumeService.getResume(resumeId, user);

        if(!resume.isPublished()) {
            return getSendMessageForNotPublishedResume(lang);
        }
        if(!resume.isCanBeUpdatedByTimePeriod(timeBetweenPublishingInHours)) {
            LocalDateTime newDate = resume.getUpdatedAt().plusHours(timeBetweenPublishingInHours);
            return getSendMessageForCanNotBeUpdatedByTimePeriod(lang, newDate);
        }

        boolean isPublished = hhResumeService.publishResume(resumeId, user);
        if(isPublished) {
            return new SendMessage().setText(
                    msg.getMessage("resume.publish.ok", lang) + ": " + resume.getTitle()
            );
        }
        return getSendMessageForErrorPublished(lang);

    }

    private SendMessage getSendMessageForUnauthorizedUser(String lang) {
        return new SendMessage()
                .setText(msg.getMessage("message.not.authorized", lang));
    }

    private SendMessage getSendMessageForNotPublishedResume(String lang) {
        return new SendMessage()
                .setText(msg.getMessage("resume.publish.warn1", lang));
    }

    private SendMessage getSendMessageForCanNotBeUpdatedByTimePeriod(String lang, LocalDateTime newDate) {
        return new SendMessage()
                .setText(
                        msg.getMessage("resume.publish.warn2", lang) + " " + Utils.getFormattingData(newDate)
                );
    }

    private SendMessage getSendMessageForErrorPublished(String lang) {
        return new SendMessage()
                .setText(msg.getMessage("message.error", lang));
    }
}

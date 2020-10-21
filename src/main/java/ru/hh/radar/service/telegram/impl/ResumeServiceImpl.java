package ru.hh.radar.service.telegram.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.dto.ResumeResultsDTO;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.service.common.UserService;
import ru.hh.radar.service.hh.HhResumeService;
import ru.hh.radar.service.telegram.InlineKeyboardService;
import ru.hh.radar.service.telegram.ResumeService;
import ru.hh.radar.telegram.service.MessageService;
import ru.hh.radar.telegram.service.TelegramMessageService;

@Slf4j
@RequiredArgsConstructor
@Service
public class ResumeServiceImpl implements ResumeService {

    private final InlineKeyboardService inlineKeyboardService;
    private final TelegramMessageService tgmMessageService;

    private final MessageService msg;
    private final UserService userService;
    private final HhResumeService hhResumeService;

    public SendMessage showAllResume(Update update) throws TelegramApiException {
        String lang = userService.getLocaleForAnswerToUser(update);
        User user = userService.findUser(update);

        if(!user.isAuthorized()) {
            log.warn("Error getting the resume list for the user " + user.getUsername());
            return new SendMessage()
                    .setChatId(user.getChatId())
                    .setText(msg.getMessage("message.not.authorized", lang));
        }

        ResumeResultsDTO results = hhResumeService.getHhUserResume(user);
        return tgmMessageService.createButtonMessage(
                userService.findUser(update).getChatId(),
                inlineKeyboardService.getResumeMenu(lang, results.getItems())
        );
    }
}

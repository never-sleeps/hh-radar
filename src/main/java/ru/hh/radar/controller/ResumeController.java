package ru.hh.radar.controller;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.service.Utils;
import ru.hh.radar.service.common.UserService;
import ru.hh.radar.service.telegram.ResumeService;
import ru.hh.radar.telegram.annotations.BotController;
import ru.hh.radar.telegram.annotations.BotRequestMapping;
import ru.hh.radar.telegram.service.IncomingUpdateService;

import java.util.List;

@BotController
@RequiredArgsConstructor
public class ResumeController {

    private final IncomingUpdateService incomingUpdateService;
    private final UserService userService;
    private final ResumeService resumeService;

    @BotRequestMapping("resume.all")
    public List<SendMessage> showAllResume(Update update) throws TelegramApiException {
        String lang = incomingUpdateService.getLanguageCode(update);
        User user = userService.findUser(
                incomingUpdateService.getUserName(update)
        );
        List<SendMessage> messages = resumeService.showAllResume(user, lang);
        messages.forEach(sendMessage -> {
            try {
                sendMessage.setChatId(incomingUpdateService.getChatId(update));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        });
        return messages;
    }

    @BotRequestMapping("resume.publish")
    public SendMessage publishResumeMenu(Update update) throws TelegramApiException {
        User user = userService.findUser(
                incomingUpdateService.getUserName(update)
        );
        String lang = incomingUpdateService.getLanguageCode(update);
        return resumeService.showPublishResumeMenu(user, lang)
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @BotRequestMapping("/publish")
    public SendMessage publishResume(Update update) throws TelegramApiException {
        User user = userService.findUser(
                incomingUpdateService.getUserName(update)
        );
        String resumeId = Utils.getCommandValue(incomingUpdateService.getCommand(update));
        String lang = incomingUpdateService.getLanguageCode(update);
        return resumeService.publishResume(user, resumeId, lang)
                .setChatId(incomingUpdateService.getChatId(update));
    }
}

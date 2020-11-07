package ru.hh.radar.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.service.common.UserService;
import ru.hh.radar.telegram.annotations.BotController;
import ru.hh.radar.telegram.annotations.BotRequestMapping;
import ru.hh.radar.telegram.service.IncomingUpdateService;
import ru.hh.radar.telegram.service.MessageService;
import ru.hh.radar.telegram.service.TelegramMessageService;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
@Slf4j
@BotController
@RequiredArgsConstructor
@Api("Сервис уведомления пользователей перед остановкой бота")
public class StopController {

    private final IncomingUpdateService incomingUpdateService;
    private final TelegramMessageService telegramMessageService;
    private final MessageService messageService;
    private final UserService userService;

    @Value("${telegram.bot.stop.message}")
    private String manualStopMessage;

    @ApiOperation("Отправка всем пользователям предупреждения об остановке бота. Доступно только для админа бота.")
    @BotRequestMapping("/stop")
    public List<SendMessage> showStopMessageForAllUsers(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserId(update));
        String lang = incomingUpdateService.getLanguageCode(update);

        List<SendMessage> messages = new ArrayList<>();
        if (userService.checkUserIsAdmin(user)) {
            String text = (manualStopMessage.trim().length() > 0) ? manualStopMessage
                    : messageService.getMessage("message.stop.bot", lang);
            for (User u: userService.findAll()) {
                SendMessage message = telegramMessageService.createMessage(text).setChatId(u.getUserId());
                messages.add(message);
            }
        }
        return messages;
    }
}

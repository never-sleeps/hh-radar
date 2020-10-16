package ru.hh.radar.service.telegram.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.model.User;
import ru.hh.radar.service.telegram.StartService;
import ru.hh.radar.service.common.UserService;
import ru.hh.radar.telegram.service.MessageService;
import ru.hh.radar.telegram.service.TelegramElementService;
import ru.hh.radar.telegram.service.TelegramMessageService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StartServiceImpl implements StartService {

    private final UserService userService;
    private final TelegramElementService telegramElementService;
    private final TelegramMessageService telegramMessageService;
    private final MessageService messageService;

    @Override
    public SendMessage showStartMenu(Update update) throws TelegramApiException {
        User user = userService.findUser(update);
        String languageCode = userService.getLocaleForAnswerToUser(update);

        return telegramMessageService.createMenuMessage(
                user.getChatId(),
                messageService.getMessage("welcome", languageCode),
                getStartMenu(languageCode)
        );
    }

    private ReplyKeyboardMarkup getStartMenu(String languageCode) {
        List<KeyboardRow> list = telegramElementService.createKeyboardRow(
                telegramElementService.createKeyboardRow(messageService.getMessage("start.search", languageCode))
        );
        return telegramElementService.createReplyKeyboardMarkup(list);
    }
}

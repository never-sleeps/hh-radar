package ru.hh.radar.service.telegram.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.dto.HhUserDTO;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.service.hh.HhUserService;
import ru.hh.radar.service.telegram.StartService;
import ru.hh.radar.service.common.UserService;
import ru.hh.radar.telegram.service.MessageService;
import ru.hh.radar.telegram.service.TelegramElementService;
import ru.hh.radar.telegram.service.TelegramMessageService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StartServiceImpl implements StartService {

    private final UserService userService;
    private final TelegramElementService telegramElementService;
    private final TelegramMessageService telegramMessageService;
    private final MessageService messageService;

    private final HhUserService hhUserService;

    @Override
    public SendMessage showStartMenu(Update update) throws TelegramApiException {
        User user = userService.findUser(update);
        String lang = userService.getLocaleForAnswerToUser(update);

        String text = messageService.getMessage("welcome", lang);
        if (user.isAuthorized()) {
            HhUserDTO hhUserDTO = hhUserService.getHhUserInfo(user);
            text = hhUserDTO.toString().concat(" ").concat(text);
        }
        return telegramMessageService.createMenuMessage(user.getChatId(), text, getStartMenu(lang));
    }

    private ReplyKeyboardMarkup getStartMenu(String lang) {
        List<KeyboardRow> list = telegramElementService.createKeyboardRow(
                telegramElementService.createKeyboardRow(messageService.getMessage("search.start", lang)),
                telegramElementService.createKeyboardRow(messageService.getMessage("authorize.user", lang))
        );
        return telegramElementService.createReplyKeyboardMarkup(list);
    }
}

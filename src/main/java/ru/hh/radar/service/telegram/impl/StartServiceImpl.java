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
    private final TelegramElementService tgmElementService;
    private final TelegramMessageService tgmMessageService;
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
        return tgmMessageService.createMenuMessage(user.getChatId(), text, getStartMenu(lang, user.isAuthorized()));
    }

    private ReplyKeyboardMarkup getStartMenu(String lang, boolean isAuthorized) {

        List<KeyboardRow> list = tgmElementService.createKeyboardRow(
                tgmElementService.createKeyboardRow(messageService.getMessage("search.start", lang)),
                tgmElementService.createKeyboardRow(
                        ((isAuthorized) ? "✅ " : "") + messageService.getMessage("authorize.user", lang)
                ),
                tgmElementService.createKeyboardRow(messageService.getMessage("all.resume", lang))
        );
        return tgmElementService.createReplyKeyboardMarkup(list);
    }
}

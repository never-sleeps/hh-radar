package ru.hh.radar.service.telegram.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.hh.radar.dto.HhUserDTO;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.service.hh.HhUserService;
import ru.hh.radar.service.telegram.StartService;
import ru.hh.radar.telegram.service.KeyboardService;
import ru.hh.radar.telegram.service.MessageService;
import ru.hh.radar.telegram.service.TelegramMessageService;

@Slf4j
@Service
@RequiredArgsConstructor
public class StartServiceImpl implements StartService {

    private final TelegramMessageService tgmMessageService;
    private final MessageService messageService;
    private final KeyboardService keyboardService;

    private final HhUserService hhUserService;

    @Override
    public SendMessage showStartMenu(User user, String lang) {
        String text = messageService.getMessage("welcome", lang);
        if (user.isAuthorized()) {
            HhUserDTO hhUserDTO = hhUserService.getHhUserInfo(user);
            text = hhUserDTO.toString().concat(" ").concat(text);
        }
        ReplyKeyboardMarkup startMenu = keyboardService.getStartMenu(lang, user.isAuthorized());
        return tgmMessageService.createMenuMessage(text, startMenu);
    }
}

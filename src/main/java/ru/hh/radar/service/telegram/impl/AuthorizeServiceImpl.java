package ru.hh.radar.service.telegram.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.service.hh.HhOauthService;
import ru.hh.radar.service.telegram.AuthorizeService;
import ru.hh.radar.telegram.service.TelegramElementService;
import ru.hh.radar.telegram.service.TelegramMessageService;

@SuppressWarnings("unchecked")
@Service
@RequiredArgsConstructor
public class AuthorizeServiceImpl implements AuthorizeService {

    private final TelegramMessageService tgmMessageService;
    private final TelegramElementService tgmElementService;

    private final HhOauthService hhOauthService;

    @Override
    public SendMessage showAuthorizeButton(User user, String lang) {
        String link = hhOauthService.getUserAuthorizeURI().toString();

        InlineKeyboardButton linkButton = tgmElementService.createUrlButton("Подтвердить авторизацию", link);
        InlineKeyboardMarkup inlineKeyboardMarkup = tgmElementService.createInlineKeyboardMarkup(
                tgmElementService.createInlineKeyboardRows(
                        tgmElementService.createInlineKeyboardRow(linkButton)
                )
        );

        return tgmMessageService.createButtonMessage(
                inlineKeyboardMarkup
        );
    }
}

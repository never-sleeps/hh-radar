package ru.hh.radar.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.service.hh.HhOauthService;
import ru.hh.radar.telegram.annotations.BotController;
import ru.hh.radar.telegram.annotations.BotRequestMapping;
import ru.hh.radar.telegram.service.IncomingUpdateService;
import ru.hh.radar.telegram.service.MessageService;
import ru.hh.radar.telegram.service.TelegramElementService;
import ru.hh.radar.telegram.service.TelegramMessageService;

import java.util.List;

@SuppressWarnings("unchecked")
@Slf4j
@BotController
@RequiredArgsConstructor
@Api("Сервис авторизации пользователя в hh")
public class HhAuthorizeController {

    private final TelegramMessageService tgmMessageService;
    private final TelegramElementService tgmElementService;
    private final IncomingUpdateService incomingUpdateService;
    private final MessageService messageService;
    private final HhOauthService hhOauthService;

    @ApiOperation("Авторизация пользователя на hh.ru")
    @BotRequestMapping(value = "authorize.user", isLocale = true)
    public SendMessage authorizeUser(Update update) throws TelegramApiException {
        String lang = incomingUpdateService.getLanguageCode(update);
        return getAuthorizeButton(lang)
                .setChatId(incomingUpdateService.getChatId(update));
    }

    private SendMessage getAuthorizeButton(String lang) {
        String messageText = messageService.getMessage("message.authorize.approve", lang);
        String buttonText = messageService.getMessage("authorize.user.approve", lang);
        String link = hhOauthService.getUserAuthorizeURI().toString();

        InlineKeyboardButton linkButton = tgmElementService.createUrlButton(buttonText, link);
        List<InlineKeyboardButton> row = tgmElementService.createInlineKeyboardRow(linkButton);
        return tgmMessageService.createButtonMessage(
                messageText,
                tgmElementService.createInlineKeyboardMarkup(
                        tgmElementService.createInlineKeyboardRows(row)
                )
        );
    }
}

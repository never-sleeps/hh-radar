package ru.hh.radar.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.dto.HhUserDTO;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.service.common.UserService;
import ru.hh.radar.service.hh.HhOauthService;
import ru.hh.radar.service.hh.HhUserService;
import ru.hh.radar.telegram.annotations.BotController;
import ru.hh.radar.telegram.annotations.BotRequestMapping;
import ru.hh.radar.telegram.service.*;

import java.util.List;

@SuppressWarnings("unchecked")
@Slf4j
@BotController
@RequiredArgsConstructor
@Api("Сервис запуска телеграм-бота и работы со стартовым меню")
public class StartController {

    private final TelegramMessageService tgmMessageService;
    private final TelegramElementService tgmElementService;
    private final KeyboardService keyboardService;
    private final IncomingUpdateService incomingUpdateService;
    private final MessageService messageService;
    private final HhUserService hhUserService;
    private final UserService userService;
    private final HhOauthService hhOauthService;

    @ApiOperation("Старт телеграм-бота")
    @BotRequestMapping("/start")
    public SendMessage start(Update update) throws TelegramApiException {
        String userName = incomingUpdateService.getMessage(update).getFrom().getUserName();
        String command = incomingUpdateService.getCommand(update);

        User user = hhOauthService.authorizeUser(userName, command);
        String lang = incomingUpdateService.getLanguageCode(update);

        return getStartMenu(user, lang)
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @ApiOperation("Авторизация пользователя на hh.ru")
    @BotRequestMapping(value = "authorize.user", isLocale = true)
    public SendMessage authorizeUser(Update update) throws TelegramApiException {
        String lang = incomingUpdateService.getLanguageCode(update);
        return getAuthorizeButton(lang)
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @ApiOperation("Отображение главного меню бота")
    @BotRequestMapping(value = "to.main.menu", isLocale = true)
    public SendMessage showStartMenu(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserName(update));
        String lang = incomingUpdateService.getLanguageCode(update);
        return getStartMenu(user, lang)
                .setChatId(incomingUpdateService.getChatId(update));
    }

    private SendMessage getStartMenu(User user, String lang) {
        String text = messageService.getMessage("welcome", lang);
        if (user.isAuthorized()) {
            HhUserDTO hhUserDTO = hhUserService.getHhUserInfo(user);
            text = hhUserDTO.toString().concat(" ").concat(text);
        }
        ReplyKeyboardMarkup startMenu = keyboardService.getStartMenu(lang, user.isAuthorized());
        return tgmMessageService.createMenuMessage(text, startMenu);
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

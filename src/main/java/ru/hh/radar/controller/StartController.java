package ru.hh.radar.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.dto.HhUserDTO;
import ru.hh.radar.model.TelegramUserInfo;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.service.common.UserService;
import ru.hh.radar.service.hh.HhOauthService;
import ru.hh.radar.service.hh.HhUserService;
import ru.hh.radar.telegram.annotations.BotController;
import ru.hh.radar.telegram.annotations.BotRequestMapping;
import ru.hh.radar.telegram.service.IncomingUpdateService;
import ru.hh.radar.telegram.service.KeyboardService;
import ru.hh.radar.telegram.service.MessageService;
import ru.hh.radar.telegram.service.TelegramMessageService;

@SuppressWarnings("unchecked")
@Slf4j
@BotController
@RequiredArgsConstructor
@Api("Сервис запуска телеграм-бота и работы со стартовым меню")
public class StartController {

    private final TelegramMessageService tgmMessageService;
    private final KeyboardService keyboardService;
    private final IncomingUpdateService incomingUpdateService;
    private final MessageService messageService;
    private final HhUserService hhUserService;
    private final UserService userService;
    private final HhOauthService hhOauthService;

    @ApiOperation("Старт телеграм-бота")
    @BotRequestMapping("/start")
    public SendMessage start(Update update) throws TelegramApiException {
        TelegramUserInfo userInfo = incomingUpdateService.getTelegramUserInfo(update);
        String command = incomingUpdateService.getCommand(update);

        User user = hhOauthService.authorizeUser(userInfo, command);
        String lang = incomingUpdateService.getLanguageCode(update);

        String text = messageService.getMessage("welcome", lang);
        if (user.isAuthorized()) {
            HhUserDTO hhUserDTO = hhUserService.getHhUserInfo(user);
            text = hhUserDTO.toString().concat(" ").concat(text);
        }
        return getStartMenu(user, text, lang)
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @ApiOperation("Отображение главного меню бота")
    @BotRequestMapping(value = "to.main.menu", isLocale = true)
    public SendMessage showStartMenu(Update update) throws TelegramApiException {
        User user = userService.findUser(incomingUpdateService.getUserId(update));
        String lang = incomingUpdateService.getLanguageCode(update);
        String text = messageService.getMessage("main.menu", lang);
        return getStartMenu(user, text, lang)
                .setChatId(incomingUpdateService.getChatId(update));
    }

    private SendMessage getStartMenu(User user, String text, String lang) {
        ReplyKeyboardMarkup startMenu = keyboardService.getStartMenu(lang, user.isAuthorized());
        return tgmMessageService.createMenuMessage(text, startMenu);
    }
}

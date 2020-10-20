package ru.hh.radar.forTesting;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.dto.VacancyDTO;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.service.Utils;
import ru.hh.radar.service.common.UserService;
import ru.hh.radar.service.hh.HhVacancyService;
import ru.hh.radar.telegram.service.TelegramElementService;
import ru.hh.radar.telegram.service.TelegramMessageService;
import ru.hh.radar.telegram.service.TelegramService;

import java.util.*;

@SuppressWarnings("unchecked")
@Service
@RequiredArgsConstructor
public class PointService {

    private final TelegramElementService telegramElementService;
    private final TelegramMessageService telegramMessageService;
    private final TelegramService telegramService;
    private final UserService userService;
    private final HhVacancyService hhVacancyService;

    public SendMessage showText(Update update) throws TelegramApiException {
        User user = userService.findUser(update);
        return new SendMessage()
                .setChatId(user.getChatId())
                .setText("helllllo");
    }

    public SendMessage showButton(Update update) throws TelegramApiException {
        User user = userService.findUser(update);
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(
                telegramElementService.createInlineKeyboardRow(
                        telegramElementService.createUrlButton("Open Browser", "https://www.google.com/"),
                        telegramElementService.createCallbackButton("Send Command", "/text")
                )
        );
        InlineKeyboardMarkup markupInline = telegramElementService.createInlineKeyboardMarkup(rowsInline);
        return telegramMessageService.createButtonMessage(
                user.getChatId(),
                "Текст самого сообщения",
                markupInline
        );
    }

    public SendMessage showMenu(Update update) throws TelegramApiException {
        User user = userService.findUser(update);

        List<KeyboardRow> list = telegramElementService.createKeyboardRow(
                telegramElementService.createKeyboardRow("Популярное", "Новости\uD83D\uDCF0"),
                telegramElementService.createKeyboardRow("Полезная Информация")
        );

        ReplyKeyboardMarkup replyKeyboardMarkup = telegramElementService.createReplyKeyboardMarkup(list);
        return telegramMessageService.createMenuMessage(
                user.getChatId(),
                "Текст самого сообщения",
                replyKeyboardMarkup
        );
    }

    public SendMessage showHh(Update update) throws TelegramApiException {
        User user = userService.findUser(update);
        VacancyDTO vacancy = hhVacancyService.getVacancy(4694457L);

        List<List<InlineKeyboardButton>> rowsInline = telegramElementService.createInlineKeyboardRows(
                telegramElementService.createInlineKeyboardRow(
                        telegramElementService.createUrlButton("Open Browser", vacancy.getAlternateUrl()),
                        telegramElementService.createUrlButton("Open Browser", vacancy.getAlternateUrl())
                )
        );
        return telegramMessageService.createButtonMessage(
                user.getChatId(),
                vacancy.toString(),
                telegramElementService.createInlineKeyboardMarkup(rowsInline)
        );
    }

    public SendMessage showCommandText(Update update) throws TelegramApiException {
        String commandValue = Utils.getCommandValue(telegramService.getCommand(update));
        User user = userService.findUser(update);
        return new SendMessage()
                .setChatId(user.getChatId())
                .setText(commandValue);
    }
}

package ru.hh.radar.service.telegram;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.model.User;
import ru.hh.radar.telegram.service.TelegramElementService;
import ru.hh.radar.telegram.service.TelegramMessageService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {

    private final TelegramElementService telegramElementService;
    private final TelegramMessageService telegramMessageService;
    private final UserService userService;

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
}

package ru.hh.radar.service.telegram;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.hh.radar.telegram.service.TelegramElementService;
import ru.hh.radar.telegram.service.TelegramMessageService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {

    private final TelegramElementService telegramElementService;
    private final TelegramMessageService telegramMessageService;

    public SendMessage testPoint(Update update) {
        return new SendMessage()
                .setChatId(update.getMessage().getChatId().toString())
                .setText("helllllo");
    }

    public SendMessage test(Update update) {

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(
                telegramElementService.createInlineKeyboardRow(
                        telegramElementService.createUrlButton("Open Browser", "https://www.google.com/"),
                        telegramElementService.createCallbackButton("Send Command", "/point")
                )
        );
        InlineKeyboardMarkup markupInline = telegramElementService.createInlineKeyboardMarkup(rowsInline);
        return telegramMessageService.createButtonMessage(
                update.getMessage().getChatId(),
                "Текст самого сообщения",
                markupInline
        );
    }

}

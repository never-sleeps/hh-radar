package ru.hh.radar.telegram.handler.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.service.telegram.TelegramElementService;
import ru.hh.radar.service.telegram.TelegramMessageService;
import ru.hh.radar.telegram.TelegramBot;
import ru.hh.radar.telegram.handler.RequestHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class StartRequestHandler implements RequestHandler {

    private final TelegramMessageService telegramMessageService;
    private final TelegramElementService telegramElementService;
    private final MessageSource messageSource;

    @Override
    public void handle(
            String text,
            Update update,
            Map<Long, String> searchMap,
            TelegramBot telegramBot
    ) throws TelegramApiException {
        log.info(update.toString());

//        String start = messageSource.getMessage("start", null, Locale.getDefault());
//        if (!text.equals(start)) return;
//        Long chatId = update.getMessage().getChatId();
//        SendMessage startSearch = prepareMessageService.prepareMessage(chatId, "Привет!");
//        telegramBot.execute(startSearch);

        if (!text.equals("test")) return;
        Long chatId = update.getMessage().getChatId();


        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(
                telegramElementService.createInlineKeyboardRow(
                    telegramElementService.createUrlButton("Open Browser", "https://www.google.com/"),
                    telegramElementService.createCallbackButton("Send Command", "/command")
                )
        );

        InlineKeyboardMarkup markupInline = telegramElementService.createInlineKeyboardMarkup(rowsInline);
        SendMessage startSearch = telegramMessageService.createButtonMessage(chatId, "Текст самого сообщения", markupInline);
        telegramBot.execute(startSearch);
    }
}

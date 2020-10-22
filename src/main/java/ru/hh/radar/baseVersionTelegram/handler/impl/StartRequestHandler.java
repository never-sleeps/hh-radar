package ru.hh.radar.baseVersionTelegram.handler.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.hh.radar.baseVersionTelegram.handler.RequestHandler;
import ru.hh.radar.telegram.TelegramBot;
import ru.hh.radar.telegram.service.TelegramElementService;
import ru.hh.radar.telegram.service.TelegramMessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
//@Component
@Deprecated
public class StartRequestHandler implements RequestHandler {

    private final TelegramMessageService telegramMessageService;
    private final TelegramElementService telegramElementService;
    private final MessageSource messageSource;

    @Override
    public void handle(
            String text,
            Update update,
            TelegramBot telegramBot
    ) {
        log.info(update.toString());

        try {
            telegramBot.execute(getTextSendMessage(text, update));
        } catch (Exception ignored){}

        try {
            telegramBot.execute(getButtonSendMessage(text, update));
        } catch (Exception ignored){}

    }

    private SendMessage getTextSendMessage(String text, Update update) {
        String start = messageSource.getMessage("start", null, Locale.getDefault());

        if (!text.equals(start)) return null;

        Long chatId = update.getMessage().getChatId();
        return telegramMessageService.createMessage(chatId, "Привет!");
    }

    private SendMessage getButtonSendMessage(String text, Update update) {
        if (!text.equals("test")) return null;
        Long chatId = update.getMessage().getChatId();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(
                telegramElementService.createInlineKeyboardRow(
                        telegramElementService.createUrlButton("Open Browser", "https://www.google.com/"),
                        telegramElementService.createCallbackButton("Send Command", "/command")
                )
        );
        InlineKeyboardMarkup markupInline = telegramElementService.createInlineKeyboardMarkup(rowsInline);
        return telegramMessageService.createButtonMessage(chatId, "Текст самого сообщения", markupInline);
    }
}

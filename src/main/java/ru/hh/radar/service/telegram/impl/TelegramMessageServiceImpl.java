package ru.hh.radar.service.telegram.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.hh.radar.service.telegram.TelegramMessageService;

@RequiredArgsConstructor
@Service
public class TelegramMessageServiceImpl implements TelegramMessageService {

    @Override
    public SendMessage createMessage(Long chatId, String text) {
        return new SendMessage()
                .enableMarkdown(true)
                .setChatId(chatId)
                .setText(text);
    }

    @Override
    public SendMessage createHtmlMessage(Long chatId, String html) {
        return new SendMessage()
                .enableHtml(true)
                .setChatId(chatId)
                .setText(html);
    }

    @Override
    public SendMessage createButtonMessage(Long chatId, String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
        return new SendMessage()
                .setChatId(chatId)
                .setText(text)
                .setReplyMarkup(inlineKeyboardMarkup);
    }
}

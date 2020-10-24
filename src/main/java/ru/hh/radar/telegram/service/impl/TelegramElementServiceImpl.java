package ru.hh.radar.telegram.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.hh.radar.telegram.service.MessageService;
import ru.hh.radar.telegram.service.TelegramElementService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TelegramElementServiceImpl implements TelegramElementService {

    private final MessageService msg;

    @Override
    public InlineKeyboardButton createUrlButton(String text, String url) {
        return new InlineKeyboardButton()
                .setText(text)
                .setUrl(url);
    }

    @Override
    public InlineKeyboardButton createCallbackButton(String text, String callbackData) {
        return new InlineKeyboardButton()
                .setText(text)
                .setCallbackData(callbackData);
    }

    @Override
    public InlineKeyboardButton createAutoCallbackButton(String command, String lang) {
        return createCallbackButton(
                msg.getMessage(command, lang), "/" + command
        );
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboardMarkup(List<List<InlineKeyboardButton>> rowsInline) {
        return new InlineKeyboardMarkup()
                .setKeyboard(rowsInline);
    }

    @Override
    public List<List<InlineKeyboardButton>> createInlineKeyboardRows(List<InlineKeyboardButton>... list) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        Collections.addAll(rowsInline, list);
        return rowsInline;
    }

    @Override
    public List<InlineKeyboardButton> createInlineKeyboardRow(InlineKeyboardButton... inlineKeyboardButtons) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        Collections.addAll(row, inlineKeyboardButtons);
        return row;
    }

    @Override
    public KeyboardRow createKeyboardRow(String... texts) {
        KeyboardRow row = new KeyboardRow();
        for (String t : texts) {
            row.add(t);
        }
        return row;
    }

    @Override
    public List<KeyboardRow> createKeyboardRow(KeyboardRow... keyboardRows) {
        List<KeyboardRow> row = new ArrayList<>();
        Collections.addAll(row, keyboardRows);
        return row;
    }

    @Override
    public ReplyKeyboardMarkup createReplyKeyboardMarkup(List<KeyboardRow> keyboard) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }
}

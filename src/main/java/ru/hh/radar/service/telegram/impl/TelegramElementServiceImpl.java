package ru.hh.radar.service.telegram.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.hh.radar.service.telegram.TelegramElementService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TelegramElementServiceImpl implements TelegramElementService {

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
    public InlineKeyboardMarkup createInlineKeyboardMarkup(List<List<InlineKeyboardButton>> rowsInline) {
        return new InlineKeyboardMarkup()
                .setKeyboard(rowsInline);
    }

    @Override
    public List<InlineKeyboardButton> createInlineKeyboardRow(InlineKeyboardButton... inlineKeyboardButtons) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        Collections.addAll(row, inlineKeyboardButtons);
        return row;
    }
}

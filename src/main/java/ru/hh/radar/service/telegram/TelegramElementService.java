package ru.hh.radar.service.telegram;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public interface TelegramElementService {

    InlineKeyboardButton createUrlButton(String text, String url);

    InlineKeyboardButton createCallbackButton(String text, String callbackData);

    InlineKeyboardMarkup createInlineKeyboardMarkup(List<List<InlineKeyboardButton>> rowsInline);

    List<InlineKeyboardButton> createInlineKeyboardRow(InlineKeyboardButton... inlineKeyboardButtons);
}

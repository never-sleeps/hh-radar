package ru.hh.radar.telegram.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public interface TelegramElementService {

    InlineKeyboardButton createUrlButton(String text, String url);

    InlineKeyboardButton createCallbackButton(String text, String callbackData);

    InlineKeyboardMarkup createInlineKeyboardMarkup(List<List<InlineKeyboardButton>> rowsInline);

    List<InlineKeyboardButton> createInlineKeyboardRow(InlineKeyboardButton... inlineKeyboardButtons);

    KeyboardRow createKeyboardRow(String... texts);

    List<KeyboardRow> createKeyboardRow(KeyboardRow... keyboardRows);

    ReplyKeyboardMarkup createReplyKeyboardMarkup(List<KeyboardRow> keyboard);
}

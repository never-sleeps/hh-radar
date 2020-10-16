package ru.hh.radar.telegram.service;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public interface TelegramLocaleElementService {

    InlineKeyboardButton createAutoCallbackButton(String command, String languageCode);

    String getValueFromCallbackQueryData (Update update);
}

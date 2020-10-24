package ru.hh.radar.telegram.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public interface TelegramElementService {

    /**
     *
     * @param text - Текст на кнопке
     * @param url - Опционально. URL, который откроется при нажатии на кнопку
     * @return InlineKeyboardButton - объект представляет одну кнопку встроенной клавиатуры.
     * Вы обязательно должны задействовать ровно одно опциональное поле.
     */
    InlineKeyboardButton createUrlButton(String text, String url);

    /**
     *
     * @param text - Текст на кнопке
     * @param callbackData - Опционально. Данные, которые будут отправлены в callback_query при нажатии на кнопку
     * @return InlineKeyboardButton - объект представляет одну кнопку встроенной клавиатуры.
     * Вы обязательно должны задействовать ровно одно опциональное поле.
     */
    InlineKeyboardButton createCallbackButton(String text, String callbackData);

    InlineKeyboardButton createAutoCallbackButton(String command, String lang);

    /**
     *
     * @param rowsInline - Массив строк, каждая из которых является массивом объектов InlineKeyboardButton.
     * @return InlineKeyboardMarkup -  объект представляет встроенную клавиатуру, которая появляется под соответствующим сообщением.
     */
    InlineKeyboardMarkup createInlineKeyboardMarkup(List<List<InlineKeyboardButton>> rowsInline);

    List<List<InlineKeyboardButton>> createInlineKeyboardRows(List<InlineKeyboardButton>... list);

    List<InlineKeyboardButton> createInlineKeyboardRow(InlineKeyboardButton... inlineKeyboardButtons);

    KeyboardRow createKeyboardRow(String... texts);

    List<KeyboardRow> createKeyboardRow(KeyboardRow... keyboardRows);

    /**
     *
     * https://core.telegram.org/bots#keyboards
     * @param keyboard - Массив рядов кнопок, каждый из которых является массивом объектов KeyboardButton
     * @return ReplyKeyboardMarkup - этот объект представляет клавиатуру с опциями ответа
     */
    ReplyKeyboardMarkup createReplyKeyboardMarkup(List<KeyboardRow> keyboard);
}

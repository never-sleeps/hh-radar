package ru.hh.radar.service.telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

/**
 *
 */
public interface TelegramMessageService {

    SendMessage createMessage(Long chatId, String text);

    SendMessage createHtmlMessage(Long chatId, String html);

    SendMessage createButtonMessage(Long chatId, String text, InlineKeyboardMarkup inlineKeyboardMarkup);
}

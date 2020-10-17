package ru.hh.radar.telegram.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.hh.radar.dto.VacancyDTO;

import java.util.List;

/**
 *
 */
public interface TelegramMessageService {

    SendMessage createMessage(Long chatId, String text);

    SendMessage createHtmlMessage(Long chatId, String html);

    SendMessage createButtonMessage(Long chatId, String text, InlineKeyboardMarkup inlineKeyboardMarkup);

    SendMessage createButtonMessage(Long chatId, InlineKeyboardMarkup inlineKeyboardMarkup);

    SendMessage createMenuMessage(Long chatId, String text, ReplyKeyboardMarkup replyKeyboardMarkup);

    List<SendMessage> createVacancyMessages(Long chatId, String linkText, List<VacancyDTO> vacancies);
}

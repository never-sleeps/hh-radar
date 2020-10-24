package ru.hh.radar.telegram.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.hh.radar.dto.ResumeDTO;
import ru.hh.radar.dto.ResumeStatusDTO;
import ru.hh.radar.dto.VacancyDTO;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface TelegramMessageService {

    SendMessage createMessage(String text);

    SendMessage createButtonMessage(String text, InlineKeyboardMarkup inlineKeyboardMarkup);

    SendMessage createButtonMessage(InlineKeyboardMarkup inlineKeyboardMarkup);

    SendMessage createMenuMessage(String text, ReplyKeyboardMarkup replyKeyboardMarkup);

    List<SendMessage> createVacancyMessages(String linkText, List<VacancyDTO> vacancies);

    List<SendMessage> createResumeMessages(String lang, Map<ResumeDTO, ResumeStatusDTO> resumeList);
}

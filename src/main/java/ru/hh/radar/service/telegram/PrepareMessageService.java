package ru.hh.radar.service.telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 *
 */
public interface PrepareMessageService {

    SendMessage prepareMessage(Long chatId, String text);

    SendMessage prepareHtmlMessage(Long chatId, String html);
}

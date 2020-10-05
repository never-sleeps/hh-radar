package ru.hh.radar.service.telegram.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.hh.radar.service.telegram.PrepareMessageService;

@RequiredArgsConstructor
@Service
public class PrepareMessageServiceImpl implements PrepareMessageService {

    @Override
    public SendMessage prepareMessage(Long chatId, String text) {
        return new SendMessage()
                .enableMarkdown(true)
                .setChatId(chatId)
                .setText(text);
    }

    @Override
    public SendMessage prepareHtmlMessage(Long chatId, String html) {
        return new SendMessage()
                .enableHtml(true)
                .setChatId(chatId)
                .setText(html);
    }
}

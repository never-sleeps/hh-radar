package ru.hh.radar.telegram.handler.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.service.telegram.PrepareMessageService;
import ru.hh.radar.telegram.TelegramBot;
import ru.hh.radar.telegram.handler.RequestHandler;

import java.util.Locale;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class StartRequestHandler implements RequestHandler {

    private final PrepareMessageService prepareMessageService;
    private final MessageSource messageSource;

    @Override
    public void handle(
            String text,
            Update update,
            Map<Long, String> searchMap,
            TelegramBot telegramBot
    ) throws TelegramApiException {
        log.info(update.toString());

        String start = messageSource.getMessage("start", null, Locale.getDefault());
        if (!text.equals(start)) return;

        Long chatId = update.getMessage().getChatId();

        SendMessage startSearch = prepareMessageService.prepareMessage(chatId, "Привет!");
        telegramBot.execute(startSearch);
    }
}

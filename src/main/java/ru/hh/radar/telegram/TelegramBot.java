package ru.hh.radar.telegram;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.telegram.handler.RequestHandler;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
//@ConfigurationProperties("telegram.bot")
@Component
public class TelegramBot extends TelegramLongPollingBot {

//    private final Map<Long, SearchDto> searchMap = new HashMap<>();
    private final List<RequestHandler> handlers;

    @Value("${telegram.bot.name}")
    private String botUserName;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String text = update.getMessage().getText();
        handlers.forEach(h -> {
            try {
                h.handle(text, update, null, this);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        });
    }
}

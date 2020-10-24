package ru.hh.radar.telegram.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.hh.radar.telegram.service.KeyboardService;
import ru.hh.radar.telegram.service.MessageService;
import ru.hh.radar.telegram.service.TelegramElementService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class KeyboardServiceImpl implements KeyboardService {

    private final TelegramElementService tgmElementService;
    private final MessageService messageService;

    @Override
    public ReplyKeyboardMarkup getStartMenu(String lang, boolean isAuthorized) {
        List<KeyboardRow> list = tgmElementService.createKeyboardRow(
                tgmElementService.createKeyboardRow(messageService.getMessage("search.start", lang)),
                tgmElementService.createKeyboardRow(
                        ((isAuthorized) ? "✅ " : "") + messageService.getMessage("authorize.user", lang)
                ),
                tgmElementService.createKeyboardRow(messageService.getMessage("resume.all", lang)),
                tgmElementService.createKeyboardRow(messageService.getMessage("resume.publish", lang))
        );
        return tgmElementService.createReplyKeyboardMarkup(list);
    }
}

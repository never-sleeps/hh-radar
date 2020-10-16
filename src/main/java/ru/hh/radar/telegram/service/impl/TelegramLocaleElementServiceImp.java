package ru.hh.radar.telegram.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.hh.radar.telegram.service.MessageService;
import ru.hh.radar.telegram.service.TelegramLocaleElementService;
import ru.hh.radar.telegram.service.TelegramElementService;

@Service
@RequiredArgsConstructor
public class TelegramLocaleElementServiceImp implements TelegramLocaleElementService {

    private final TelegramElementService tgmElementService;
    private final MessageService msg;


    @Override
    public InlineKeyboardButton createAutoCallbackButton(String command, String languageCode) {
        return tgmElementService.createCallbackButton(
                msg.getMessage(command, languageCode), "/" + command);
    }

    @Override
    public String getValueFromCallbackQueryData(Update update) {
        String callbackQueryData = update.getCallbackQuery().getData();
        return callbackQueryData.substring(callbackQueryData.lastIndexOf(".") + 1);
    }
}

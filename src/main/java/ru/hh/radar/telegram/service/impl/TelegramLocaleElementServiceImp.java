package ru.hh.radar.telegram.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.hh.radar.telegram.service.MessageService;
import ru.hh.radar.telegram.service.TelegramLocaleElementService;
import ru.hh.radar.telegram.service.TelegramElementService;

/*

 */
@Service
@RequiredArgsConstructor
public class TelegramLocaleElementServiceImp implements TelegramLocaleElementService {

    private final TelegramElementService tgmElementService;
    private final MessageService msg;


    @Override
    public InlineKeyboardButton createAutoCallbackButton(String command, String lang) {
        return tgmElementService.createCallbackButton(
                msg.getMessage(command, lang), "/" + command);
    }

    @Override
    public String getValueFromCallbackQuery(Update update) {
        String callbackQueryData = update.getCallbackQuery().getData();
        return callbackQueryData.substring(callbackQueryData.lastIndexOf(".") + 1);
    }

    @Override
    public String getLongValueFromCallbackQuery(Update update) {
        String callbackQueryData = update.getCallbackQuery().getData();
        int indexOfPostfix = callbackQueryData.lastIndexOf(".");
        String indexOfPoint = callbackQueryData.substring(0, indexOfPostfix);
        int indexOfPrefix = indexOfPoint.lastIndexOf(".");

        return callbackQueryData.substring(indexOfPrefix + 1);
    }
}

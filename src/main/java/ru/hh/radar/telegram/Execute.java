package ru.hh.radar.telegram;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.telegram.reflection.BotApiMethodContainer;
import ru.hh.radar.telegram.reflection.BotApiMethodController;

import java.util.List;

@Slf4j
public class Execute {

    private static final BotApiMethodContainer CONTAINER = BotApiMethodContainer.getInstance();

    public static List<BotApiMethod> getExecutors(Update update) throws TelegramApiException {
        BotApiMethodController controller = getHandle(update);
        return controller.process(update);
    }

    public static BotApiMethodController getHandle(Update update) throws TelegramApiException {
        String command = "undefined";
        if (update.hasMessage() && update.getMessage().hasText()) {
            command = update.getMessage().getText().trim();
            return CONTAINER.getBotApiMethodController(command);
        } else if (update.hasCallbackQuery()) {
            command = update.getCallbackQuery().getData().trim();
            return CONTAINER.getBotApiMethodController(command);
        }
        throw new TelegramApiException("BotApiMethodController not found for command: " + command);
    }
}

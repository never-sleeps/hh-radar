package ru.hh.radar.telegram.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.service.Utils;
import ru.hh.radar.telegram.reflection.BotApiMethodContainer;
import ru.hh.radar.telegram.reflection.BotApiMethodController;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExecutorService {

    private final TelegramService telegramService;

    private final BotApiMethodContainer CONTAINER = BotApiMethodContainer.getInstance();

    public List<BotApiMethod<?>> getExecutors(Update update) throws TelegramApiException {
        BotApiMethodController controller = getHandle(update);
        return controller.process(update);
    }

    private BotApiMethodController getHandle(Update update) throws TelegramApiException {
        String command = telegramService.getCommand(update);
        String commandKey = Utils.getCommandKey(command);
        if (commandKey == null){
            log.error("Command not found for " + command);
            throw new TelegramApiException("Command not found");
        }

        BotApiMethodController controller = CONTAINER.getBotApiMethodController(commandKey);
        if (controller == null){
            log.error("Controller not found for command: " + commandKey);
            throw new TelegramApiException("Controller not found for command: " + commandKey);
        }
        return controller;
    }
}

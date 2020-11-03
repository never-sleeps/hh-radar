package ru.hh.radar.telegram.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.telegram.reflection.BotApiMethodContainer;
import ru.hh.radar.telegram.reflection.BotApiMethodController;
import ru.hh.radar.telegram.service.ExecutorService;
import ru.hh.radar.telegram.service.IncomingUpdateService;
import ru.hh.radar.utils.Utils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExecutorServiceImpl implements ExecutorService {

    private final IncomingUpdateService incomingUpdateService;

    private final BotApiMethodContainer CONTAINER = BotApiMethodContainer.getInstance();

    @Override
    public List<BotApiMethod<?>> getExecutors(Update update) throws TelegramApiException {
        String command = incomingUpdateService.getCommand(update);
        BotApiMethodController controller = getHandle(command);
        return controller.process(update);
    }

    private BotApiMethodController getHandle(String command) throws TelegramApiException {
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

package ru.hh.radar.telegram.reflection;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class BotApiMethodContainer {

    private Map<String, BotApiMethodController> controllerMap;

    private BotApiMethodContainer() {
        this.controllerMap = new HashMap<>();
    }

    public static BotApiMethodContainer getInstance() {
        return Holder.INST;
    }

    public void addBotController(String command, BotApiMethodController controller) {
        if (controllerMap.containsKey(command)) return;

        log.info("Add telegram bot controller for command: " + command);
        this.controllerMap.put(command, controller);
    }

    public BotApiMethodController getBotApiMethodController(String command) {
        return this.controllerMap.get(command);
    }

    private static class Holder {
        private static final BotApiMethodContainer INST = new BotApiMethodContainer();
    }
}

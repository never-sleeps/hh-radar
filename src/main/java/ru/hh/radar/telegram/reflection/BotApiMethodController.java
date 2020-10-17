package ru.hh.radar.telegram.reflection;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

@Slf4j
public abstract class BotApiMethodController {

    private final Object bean;
    private final Method method;
    private final Process processUpdate;

    public BotApiMethodController(Object bean, Method method) {
        this.bean = bean;
        this.method = method;
        this.processUpdate = typeListReturnDetect() ? this::processList : this::processSingle;
    }

    public abstract boolean successUpdatePredicate(Update update);

    public List<BotApiMethod> process(Update update) throws TelegramApiException {
        try {
            return this.processUpdate.accept(update);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new TelegramApiException(e.getCause());
        }
    }

    private boolean typeListReturnDetect() {
        return List.class.equals(this.method.getReturnType());
    }


    private interface Process {
        List<BotApiMethod> accept(Update update) throws InvocationTargetException, IllegalAccessException;
    }

    private List<BotApiMethod> processSingle(Update update) throws InvocationTargetException, IllegalAccessException {
        BotApiMethod botApiMethod = (BotApiMethod) method.invoke(bean, update);
        return (botApiMethod != null) ? Collections.singletonList(botApiMethod) : Collections.emptyList();
    }

    private List<BotApiMethod> processList(Update update) throws InvocationTargetException, IllegalAccessException {
        List<BotApiMethod> botApiMethods = (List<BotApiMethod>) method.invoke(bean, update);
        return (botApiMethods != null) ? botApiMethods : Collections.emptyList();
    }
}


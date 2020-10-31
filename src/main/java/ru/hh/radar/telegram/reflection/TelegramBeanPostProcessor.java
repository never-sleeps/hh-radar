package ru.hh.radar.telegram.reflection;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.config.LocaleConfig;
import ru.hh.radar.telegram.annotations.BotController;
import ru.hh.radar.telegram.annotations.BotRequestMapping;
import ru.hh.radar.telegram.service.MessageService;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TelegramBeanPostProcessor implements BeanPostProcessor, Ordered {

    private BotApiMethodContainer container = BotApiMethodContainer.getInstance();
    private Map<String, Class> botControllerMap = new HashMap<>();
    private final MessageService messageService;
    private final LocaleConfig localeConfig;

    @Nullable
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(BotController.class)) {
            botControllerMap.put(beanName, beanClass);
        }
        return bean;
    }

    @Nullable
    @SneakyThrows
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!botControllerMap.containsKey(beanName)) return bean;

        Class original = botControllerMap.get(beanName);
        Method[] methods = original.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(BotRequestMapping.class)) {
                generateController(bean, method);
            }
        }
        return bean;
    }

    private void generateController(Object bean, Method method) throws TelegramApiException {
        BotController botController = bean.getClass().getAnnotation(BotController.class);
        BotRequestMapping botRequestMapping = method.getAnnotation(BotRequestMapping.class);

        BotApiMethodController controller = createApiMethodController(botRequestMapping, bean, method);
        for (String mapping: botRequestMapping.value()) {
            if (botRequestMapping.isLocale()) {
                for (String locale: localeConfig.getLocales()) {
                    String command = messageService.getMessage(mapping, locale);
                    container.addBotController(command, controller);
                }
            } else {
                String command = botController.value() + mapping;
                container.addBotController(command, controller);
            }
        }
    }

    private BotApiMethodController createApiMethodController(
            BotRequestMapping botRequestMapping,
            Object bean,
            Method method
    ) throws TelegramApiException {
        switch (botRequestMapping.method()) {
            case MESSAGE:
                return createApiMethodControllerForMessage(bean, method);
            case CALLBACK_QUERY:
                return createApiMethodControllerForCallbackQuery(bean, method);
        }
        throw new TelegramApiException(String.format(
                "Error creating BotApiMethodController for: bean '%s', method '%s', botRequestMapping '%s'",
                bean, method, botRequestMapping
        ));
    }

    private BotApiMethodController createApiMethodControllerForMessage(Object bean, Method method) {
        return new BotApiMethodController(bean, method) {
            @Override
            public boolean successUpdatePredicate(Update update) {
                return update != null &&
                        update.hasMessage() &&
                        update.getMessage().hasText();
            }
        };
    }

    private BotApiMethodController createApiMethodControllerForCallbackQuery(Object bean, Method method) {
        return new BotApiMethodController(bean, method) {
            @Override
            public boolean successUpdatePredicate(Update update) {
                return update != null &&
                        update.hasCallbackQuery() &&
                        update.getCallbackQuery().getData() != null;
            }
        };
    }

    @Override
    public int getOrder() { return 1; }
}

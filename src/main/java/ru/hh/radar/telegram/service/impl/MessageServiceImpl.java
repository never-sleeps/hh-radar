package ru.hh.radar.telegram.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.hh.radar.telegram.service.MessageService;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageSource messageSource;

    @Override
    public String getMessage(String text, String languageCode) {
        return messageSource.getMessage(text, null, Locale.forLanguageTag(languageCode));
    }
}

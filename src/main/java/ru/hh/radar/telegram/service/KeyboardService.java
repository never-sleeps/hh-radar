package ru.hh.radar.telegram.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public interface KeyboardService {
    ReplyKeyboardMarkup getStartMenu(String lang, boolean isAuthorized);
}

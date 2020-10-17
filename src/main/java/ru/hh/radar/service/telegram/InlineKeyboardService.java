package ru.hh.radar.service.telegram;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public interface InlineKeyboardService {

    ReplyKeyboardMarkup getMainSearchMenu(String lang);

    InlineKeyboardMarkup getScheduleMenu(String lang);

    InlineKeyboardMarkup getAreaMenu(String lang);

    InlineKeyboardMarkup getEmploymentMenu(String lang);

    InlineKeyboardMarkup getExperienceMenu(String lang);

    InlineKeyboardMarkup getItSpecializationMenu(String lang);

    InlineKeyboardMarkup getSpecializationMenu(String lang);
}

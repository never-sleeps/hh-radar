package ru.hh.radar.telegram.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.hh.radar.dto.ResumeDTO;
import ru.hh.radar.dto.TypeDTO;

import java.util.List;

public interface InlineKeyboardService {

    ReplyKeyboardMarkup getMainSearchMenu(String lang);

    InlineKeyboardMarkup getScheduleMenu(String lang);

    InlineKeyboardMarkup getEmploymentMenu(String lang);

    InlineKeyboardMarkup getExperienceMenu(String lang);

    InlineKeyboardMarkup getPagingMenu(int page, List<TypeDTO> item, String command);

    InlineKeyboardMarkup getResumeMenu(String lang, List<ResumeDTO> resumeList);
}

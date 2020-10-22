package ru.hh.radar.service.telegram;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.hh.radar.dto.ResumeDTO;

import java.util.List;

public interface InlineKeyboardService {

    ReplyKeyboardMarkup getMainSearchMenu(String lang);

    InlineKeyboardMarkup getScheduleMenu(String lang);

    InlineKeyboardMarkup getAreaMenu(String lang);

    InlineKeyboardMarkup getEmploymentMenu(String lang);

    InlineKeyboardMarkup getExperienceMenu(String lang);

    InlineKeyboardMarkup getItSpecializationMenu(String lang);

    InlineKeyboardMarkup getSpecializationMenu(String lang);

    InlineKeyboardMarkup getResumeMenu(String lang, List<ResumeDTO> resumeList);
}

package ru.hh.radar.service.telegram.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.hh.radar.dto.ResumeDTO;
import ru.hh.radar.service.telegram.InlineKeyboardService;
import ru.hh.radar.telegram.service.MessageService;
import ru.hh.radar.telegram.service.TelegramElementService;
import ru.hh.radar.telegram.service.TelegramLocaleElementService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InlineKeyboardServiceImpl implements InlineKeyboardService {

    private final TelegramElementService tgmElementService;
    private final TelegramLocaleElementService tgmLocaleElementService;
    private final MessageService msg;

    @Override
    public ReplyKeyboardMarkup getMainSearchMenu(String lang) {
        List<KeyboardRow> list = tgmElementService.createKeyboardRow(
                tgmElementService.createKeyboardRow(
                        msg.getMessage("search.specialization", lang),
                        msg.getMessage("search.area", lang)
                ),
                tgmElementService.createKeyboardRow(
                        msg.getMessage("search.experience", lang),
                        msg.getMessage("search.employment", lang),
                        msg.getMessage("search.schedule", lang)
                ),
                tgmElementService.createKeyboardRow(
                        msg.getMessage("search.run", lang),
                        msg.getMessage("cancel", lang)
                )
        );
        return tgmElementService.createReplyKeyboardMarkup(list);
    }

    @Override
    public InlineKeyboardMarkup getScheduleMenu(String lang) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.schedule.fullDay", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.schedule.shift", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.schedule.flexible", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.schedule.remote", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.schedule.flyInFlyOut", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.other", lang)
                )
        );
        return tgmElementService.createInlineKeyboardMarkup(rowsInline);
    }

    @Override
    public InlineKeyboardMarkup getAreaMenu(String lang) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.area.1", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.area.113", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.area.1438", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.area.88", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.area.1202", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.other", lang)
                )
        );
        return tgmElementService.createInlineKeyboardMarkup(rowsInline);
    }

    @Override
    public InlineKeyboardMarkup getEmploymentMenu(String lang) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.employment.full", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.employment.part", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.employment.project", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.employment.volunteer", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.employment.probation", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.other", lang)
                )
        );
        return tgmElementService.createInlineKeyboardMarkup(rowsInline);
    }

    @Override
    public InlineKeyboardMarkup getExperienceMenu(String lang) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.experience.noExperience" , lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.experience.between1And3", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.experience.between3And6", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.experience.moreThan6", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.other", lang)
                )
        );
        return tgmElementService.createInlineKeyboardMarkup(rowsInline);
    }

    @Override
    public InlineKeyboardMarkup getItSpecializationMenu(String lang) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.1.3", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.1.9", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.1.25", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.1.82", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.1.110", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.1.113", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.1.117", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.1.137", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.1.172", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.1.211", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.1.221", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.1.270", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.1.273", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.1.296", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.1.327", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.1.400", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.1.420", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.1.474", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.1.475", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.other", lang)
                )
        );
        return tgmElementService.createInlineKeyboardMarkup(rowsInline);
    }

    @Override
    public InlineKeyboardMarkup getSpecializationMenu(String lang) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.1", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.2", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.3", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.4", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.5", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.6", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.7", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.8", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.9", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.10", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.11", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.12", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.13", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.14", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.15", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.16", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.17", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.18", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.19", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.20", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.21", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.22", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.23", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.24", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.25", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.27", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.specialization.29", lang),
                        tgmLocaleElementService.createAutoCallbackButton("search.other", lang)
                )
        );
        return tgmElementService.createInlineKeyboardMarkup(rowsInline);
    }

    @Override
    public InlineKeyboardMarkup getResumeMenu(String lang, List<ResumeDTO> resumeList) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        String linkText = msg.getMessage("browser.open", lang);

        for (ResumeDTO resume : resumeList) {
            rowsInline.add(
                    tgmElementService.createInlineKeyboardRow(
                            tgmElementService.createCallbackUrlButton(
                                    "\uD83C\uDF10 " + resume.toString(),
                                    resume.getAlternateUrl(),
                                    "/" + "command"
                            )
                    )
            );
        }

        return tgmElementService.createInlineKeyboardMarkup(rowsInline);
    }
}

package ru.hh.radar.telegram.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.hh.radar.dto.ResumeDTO;
import ru.hh.radar.telegram.service.InlineKeyboardService;
import ru.hh.radar.telegram.service.MessageService;
import ru.hh.radar.telegram.service.TelegramElementService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InlineKeyboardServiceImpl implements InlineKeyboardService {

    private final TelegramElementService tgmElementService;
    private final MessageService msg;

    @Value("${headhunter.timeBetweenPublishing.hours}")
    private int timeBetweenPublishingInHours;

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
                        tgmElementService.createAutoCallbackButton("search.schedule.fullDay", lang),
                        tgmElementService.createAutoCallbackButton("search.schedule.shift", lang),
                        tgmElementService.createAutoCallbackButton("search.schedule.flexible", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmElementService.createAutoCallbackButton("search.schedule.remote", lang),
                        tgmElementService.createAutoCallbackButton("search.schedule.flyInFlyOut", lang),
                        tgmElementService.createAutoCallbackButton("search.other", lang)
                )
        );
        return tgmElementService.createInlineKeyboardMarkup(rowsInline);
    }

    @Override
    public InlineKeyboardMarkup getAreaMenu(String lang) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmElementService.createAutoCallbackButton("search.area.1", lang),
                        tgmElementService.createAutoCallbackButton("search.area.113", lang),
                        tgmElementService.createAutoCallbackButton("search.area.1438", lang),
                        tgmElementService.createAutoCallbackButton("search.area.88", lang),
                        tgmElementService.createAutoCallbackButton("search.area.1202", lang),
                        tgmElementService.createAutoCallbackButton("search.other", lang)
                )
        );
        return tgmElementService.createInlineKeyboardMarkup(rowsInline);
    }

    @Override
    public InlineKeyboardMarkup getEmploymentMenu(String lang) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmElementService.createAutoCallbackButton("search.employment.full", lang),
                        tgmElementService.createAutoCallbackButton("search.employment.part", lang),
                        tgmElementService.createAutoCallbackButton("search.employment.project", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmElementService.createAutoCallbackButton("search.employment.volunteer", lang),
                        tgmElementService.createAutoCallbackButton("search.employment.probation", lang),
                        tgmElementService.createAutoCallbackButton("search.other", lang)
                )
        );
        return tgmElementService.createInlineKeyboardMarkup(rowsInline);
    }

    @Override
    public InlineKeyboardMarkup getExperienceMenu(String lang) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmElementService.createAutoCallbackButton("search.experience.noExperience" , lang),
                        tgmElementService.createAutoCallbackButton("search.experience.between1And3", lang),
                        tgmElementService.createAutoCallbackButton("search.experience.between3And6", lang),
                        tgmElementService.createAutoCallbackButton("search.experience.moreThan6", lang),
                        tgmElementService.createAutoCallbackButton("search.other", lang)
                )
        );
        return tgmElementService.createInlineKeyboardMarkup(rowsInline);
    }

    @Override
    public InlineKeyboardMarkup getItSpecializationMenu(String lang) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmElementService.createAutoCallbackButton("search.specialization.1.3", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.1.9", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.1.25", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.1.82", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmElementService.createAutoCallbackButton("search.specialization.1.110", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.1.113", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.1.117", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.1.137", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmElementService.createAutoCallbackButton("search.specialization.1.172", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.1.211", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.1.221", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.1.270", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmElementService.createAutoCallbackButton("search.specialization.1.273", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.1.296", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.1.327", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.1.400", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmElementService.createAutoCallbackButton("search.specialization.1.420", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.1.474", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.1.475", lang),
                        tgmElementService.createAutoCallbackButton("search.other", lang)
                )
        );
        return tgmElementService.createInlineKeyboardMarkup(rowsInline);
    }

    @Override
    public InlineKeyboardMarkup getSpecializationMenu(String lang) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmElementService.createAutoCallbackButton("search.specialization.1", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.2", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.3", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.4", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmElementService.createAutoCallbackButton("search.specialization.5", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.6", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.7", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.8", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmElementService.createAutoCallbackButton("search.specialization.9", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.10", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.11", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.12", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmElementService.createAutoCallbackButton("search.specialization.13", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.14", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.15", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.16", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmElementService.createAutoCallbackButton("search.specialization.17", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.18", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.19", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.20", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmElementService.createAutoCallbackButton("search.specialization.21", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.22", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.23", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.24", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmElementService.createAutoCallbackButton("search.specialization.25", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.27", lang),
                        tgmElementService.createAutoCallbackButton("search.specialization.29", lang),
                        tgmElementService.createAutoCallbackButton("search.other", lang)
                )
        );
        return tgmElementService.createInlineKeyboardMarkup(rowsInline);
    }

    @Override
    public InlineKeyboardMarkup getResumeMenu(String lang, List<ResumeDTO> resumeList) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (ResumeDTO resume : resumeList) {
            String status = (resume.isPublished())
                    ? (resume.isCanBeUpdatedByTimePeriod(timeBetweenPublishingInHours) ? "⏰" : "✅") : "❌";
            InlineKeyboardButton button = tgmElementService.createCallbackButton(
                    status + " " + resume.toShortString(),
                    "/publish " + resume.getId()
            );
            rowsInline.add(tgmElementService.createInlineKeyboardRow(button));
        }
        return tgmElementService.createInlineKeyboardMarkup(rowsInline);
    }
}

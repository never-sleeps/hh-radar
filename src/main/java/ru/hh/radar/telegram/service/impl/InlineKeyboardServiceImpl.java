package ru.hh.radar.telegram.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.hh.radar.dto.ResumeDTO;
import ru.hh.radar.dto.TypeDTO;
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

    private static final int MAX_BUTTON = 10;

    @Override
    public ReplyKeyboardMarkup getMainSearchMenu(String lang) {
        List<KeyboardRow> list = tgmElementService.createKeyboardRow(
                tgmElementService.createKeyboardRow(
                        msg.getMessage("search.text", lang),
                        msg.getMessage("search.area", lang),
                        msg.getMessage("search.specialization", lang)
                ),
                tgmElementService.createKeyboardRow(
                        msg.getMessage("search.experience", lang),
                        msg.getMessage("search.employment", lang),
                        msg.getMessage("search.schedule", lang)
                ),
                tgmElementService.createKeyboardRow(
                        msg.getMessage("search.run", lang),
                        msg.getMessage("search.clean", lang),
                        msg.getMessage("main.menu", lang)
                )
        );
        return tgmElementService.createReplyKeyboardMarkup(list);
    }

    @Override
    public InlineKeyboardMarkup getScheduleMenu(String lang) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmElementService.createAutoCallbackButton("/search.schedule.fullDay", lang),
                        tgmElementService.createAutoCallbackButton("/search.schedule.shift", lang),
                        tgmElementService.createAutoCallbackButton("/search.schedule.flexible", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmElementService.createAutoCallbackButton("/search.schedule.remote", lang),
                        tgmElementService.createAutoCallbackButton("/search.schedule.flyInFlyOut", lang)
                )
        );
        return tgmElementService.createInlineKeyboardMarkup(rowsInline);
    }

    @Override
    public InlineKeyboardMarkup getEmploymentMenu(String lang) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmElementService.createAutoCallbackButton("/search.employment.full", lang),
                        tgmElementService.createAutoCallbackButton("/search.employment.part", lang),
                        tgmElementService.createAutoCallbackButton("/search.employment.project", lang)
                )
        );
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmElementService.createAutoCallbackButton("/search.employment.volunteer", lang),
                        tgmElementService.createAutoCallbackButton("/search.employment.probation", lang)
                )
        );
        return tgmElementService.createInlineKeyboardMarkup(rowsInline);
    }

    @Override
    public InlineKeyboardMarkup getExperienceMenu(String lang) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(
                tgmElementService.createInlineKeyboardRow(
                        tgmElementService.createAutoCallbackButton("/search.experience.noExperience" , lang),
                        tgmElementService.createAutoCallbackButton("/search.experience.between1And3", lang),
                        tgmElementService.createAutoCallbackButton("/search.experience.between3And6", lang),
                        tgmElementService.createAutoCallbackButton("/search.experience.moreThan6", lang)
                )
        );
        return tgmElementService.createInlineKeyboardMarkup(rowsInline);
    }

    @Override
    public InlineKeyboardMarkup getPagingMenu(int page, List<TypeDTO> item, String command) {
        List<List<InlineKeyboardButton>> rowsInline = getPreparedButtonRows(page, command, item);
        List<InlineKeyboardButton> pagingRow = getPagingButtonsRow(page, command, item.size());
        if(pagingRow != null) {
            rowsInline.add(pagingRow);
        }
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

    private List<List<InlineKeyboardButton>> getPreparedButtonRows(int page, String command, List<TypeDTO> items) {
        List<List<InlineKeyboardButton>> row = new ArrayList<>();
        for (int i = page * MAX_BUTTON; i < page * MAX_BUTTON + MAX_BUTTON && i < items.size(); i++) {
            TypeDTO item = items.get(i);
            row.add(tgmElementService.createInlineKeyboardRow(
                    tgmElementService.createCallbackButton(item.getName(), command + " " + item.getId())
            ));
        }
        return row;
    }

    private List<InlineKeyboardButton> getPagingButtonsRow(
            int page, String command, int buttonsCount
    ) {
        int nextPage = page + 1;
        int backPage = page - 1;
        boolean isExistsBack = backPage >= 0;
        boolean isExistsNext = isExistsNextPage(nextPage, buttonsCount);

        if(isExistsBack && isExistsNext) {
            InlineKeyboardButton buttonBack = createCallbackButton("⬅️", command + ".back", backPage);
            InlineKeyboardButton buttonNext = createCallbackButton("➡️", command + ".next", nextPage);
            return tgmElementService.createInlineKeyboardRow(buttonBack, buttonNext);
        }
        if(isExistsBack) {
            InlineKeyboardButton buttonBack = createCallbackButton("⬅️", command + ".back", backPage);
            return tgmElementService.createInlineKeyboardRow(buttonBack);
        }
        if(isExistsNext) {
            InlineKeyboardButton buttonNext = createCallbackButton("➡️", command + ".next", nextPage);
            return tgmElementService.createInlineKeyboardRow(buttonNext);
        }
        return null;
    }

    private InlineKeyboardButton createCallbackButton(String text, String command, int nextPage) {
        return tgmElementService.createCallbackButton(text, command + " " + nextPage);
    }

    private boolean isExistsNextPage(int page, int buttonsCount) {
        return page * MAX_BUTTON < buttonsCount;
    }
}

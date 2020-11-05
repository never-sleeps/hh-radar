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
import ru.hh.radar.model.SearchParametersType;
import ru.hh.radar.model.entity.Dictionary;
import ru.hh.radar.service.common.AutoPublishingResumeService;
import ru.hh.radar.service.common.DictionaryService;
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
    private final AutoPublishingResumeService autoPublishingResumeService;
    private final DictionaryService dictionaryService;

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
                        msg.getMessage("to.main.menu", lang)
                )
        );
        return tgmElementService.createReplyKeyboardMarkup(list);
    }

    @Override
    public InlineKeyboardMarkup getScheduleMenu(String lang) {
        return getSearchParametersMenu(SearchParametersType.SCHEDULE, "/search.schedule");
    }

    @Override
    public InlineKeyboardMarkup getEmploymentMenu(String lang) {
        return getSearchParametersMenu(SearchParametersType.EMPLOYMENT, "/search.employment");
    }

    private InlineKeyboardMarkup getSearchParametersMenu(SearchParametersType type, String commandKey) {
        List<Dictionary> dictionaries = dictionaryService.getDictionaryValuesByType(type);
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        for (Dictionary dictionary : dictionaries) {
            InlineKeyboardButton button = tgmElementService.createCallbackButton(
                    dictionary.getTitle(), commandKey + " " + dictionary.getParam()
            );
            rowsInline.add(tgmElementService.createInlineKeyboardRow(button));
        }
        return tgmElementService.createInlineKeyboardMarkup(rowsInline);
    }

    @Override
    public InlineKeyboardMarkup getExperienceMenu(String lang) {
        return getSearchParametersMenu(SearchParametersType.EXPERIENCE, "/search.experience");
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
    public InlineKeyboardMarkup getPublishResumeMenu(String lang, List<ResumeDTO> resumeList) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (ResumeDTO resume : resumeList) {
            String id = resume.getId();

            String publishText = (resume.isPublished())
                    ? (resume.isCanBeUpdatedByTimePeriod(timeBetweenPublishingInHours) ? "⏰" : "✅") : "\uD83D\uDEAB";
            InlineKeyboardButton publishButton = tgmElementService
                    .createCallbackButton(publishText + " " + resume.toShortString(), "/publish " + id);

            rowsInline.add(tgmElementService.createInlineKeyboardRow(publishButton));
        }
        return tgmElementService.createInlineKeyboardMarkup(rowsInline);
    }

    @Override
    public InlineKeyboardMarkup getAutoPublishResumeMenu(String lang, List<ResumeDTO> resumeList) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (ResumeDTO resume : resumeList) {
            String id = resume.getId();

            String autoPublishLabel = !resume.isPublished() ? msg.getMessage("resume.publish.auto.notavailable", lang)
                    : (autoPublishingResumeService.isAutoPublishingResume(id)
                        ? msg.getMessage("resume.publish.auto.true", lang)
                        : msg.getMessage("resume.publish.auto.false", lang)
            );
            InlineKeyboardButton autoPublishButton = tgmElementService.createCallbackButton(
                    autoPublishLabel + " " + resume.toShortString(),
                    (resume.isPublished()) ? "/auto.publish " + id : "/auto.publish.notavailable"
            );

            rowsInline.add(tgmElementService.createInlineKeyboardRow(autoPublishButton));
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

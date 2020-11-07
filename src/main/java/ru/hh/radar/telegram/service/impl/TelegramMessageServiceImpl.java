package ru.hh.radar.telegram.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.hh.radar.dto.ResumeDTO;
import ru.hh.radar.dto.VacancyDTO;
import ru.hh.radar.telegram.service.MessageService;
import ru.hh.radar.telegram.service.TelegramElementService;
import ru.hh.radar.telegram.service.TelegramMessageService;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
@Service
@RequiredArgsConstructor
public class TelegramMessageServiceImpl implements TelegramMessageService {

    private final TelegramElementService tgmElementService;
    private final MessageService msg;

    @Override
    public SendMessage createMessage(String text) {
        return new SendMessage()
                .enableMarkdown(true)
                .setText(text)
                .disableWebPagePreview();
    }

    public SendMessage createButtonMessage(String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
        return new SendMessage()
                .setText(text)
                .setReplyMarkup(inlineKeyboardMarkup)
                .disableWebPagePreview();
    }

    @Override
    public SendMessage createButtonMessage(InlineKeyboardMarkup inlineKeyboardMarkup) {
        return createButtonMessage("️✏️", inlineKeyboardMarkup);
    }

    @Override
    public SendMessage createMenuMessage(String text, ReplyKeyboardMarkup replyKeyboardMarkup) {
        return new SendMessage()
                .setText(text)
                .setReplyMarkup(replyKeyboardMarkup)
                .disableWebPagePreview();
    }

    @Override
    public List<SendMessage> createVacancyMessages(
            List<VacancyDTO> vacancies, String nextCommand, Long chatId, String lang
    ) {
        List<SendMessage> vacancyMessages = new ArrayList<>();
        for (VacancyDTO vacancy: vacancies) {
            vacancyMessages.add(
                    createMessage(vacancy.toString()).setChatId(chatId)
            );
        }
        if (vacancies.size() > 0) {
            InlineKeyboardButton linkButton = tgmElementService.createCallbackButton(
                    msg.getMessage("search.next", lang), nextCommand
            );
            InlineKeyboardMarkup keyboardMarkup = tgmElementService.createInlineKeyboardMarkup(
                    tgmElementService.createInlineKeyboardRows(
                            tgmElementService.createInlineKeyboardRow(linkButton)
                    )
            );
            vacancyMessages.add(createButtonMessage(keyboardMarkup).setChatId(chatId));
        }
        return vacancyMessages;
    }

    @Override
    public List<SendMessage> createResumeInfoMessages(List<ResumeDTO> resumeList, Long chatId, String lang) {
        List<SendMessage> resumeMessages = new ArrayList<>();
        for (ResumeDTO resume: resumeList) {
            InlineKeyboardButton button = tgmElementService.createCallbackButton(
                    msg.getMessage("search.similar.run", lang),
                    "/search.similar.run" + " " + resume.getId()
            );
            InlineKeyboardMarkup keyboardMarkup = tgmElementService.createInlineKeyboardMarkup(
                    tgmElementService.createInlineKeyboardRows(
                            tgmElementService.createInlineKeyboardRow(button)
                    )
            );
            resumeMessages.add(
                    createButtonMessage("\uD83D\uDCDD " + resume.toString(), keyboardMarkup).setChatId(chatId)
            );
        }
        return resumeMessages;
    }
}

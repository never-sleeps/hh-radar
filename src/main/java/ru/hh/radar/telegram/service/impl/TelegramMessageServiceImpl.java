package ru.hh.radar.telegram.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.hh.radar.dto.ResumeDTO;
import ru.hh.radar.dto.ResumeStatusDTO;
import ru.hh.radar.dto.VacancyDTO;
import ru.hh.radar.telegram.service.MessageService;
import ru.hh.radar.telegram.service.TelegramElementService;
import ru.hh.radar.telegram.service.TelegramMessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@Service
@RequiredArgsConstructor
public class TelegramMessageServiceImpl implements TelegramMessageService {

    private final TelegramElementService tgmElementService;
    private final MessageService msg;

    @Override
    public SendMessage createMessage(String text) {
        return new SendMessage()
                .enableMarkdown(false)
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
    public List<SendMessage> createVacancyMessages(List<VacancyDTO> vacancies, Long chatId, String lang) {
        List<SendMessage> vacancyMessages = new ArrayList<>();
        for (VacancyDTO vacancy: vacancies) {
            vacancyMessages.add(
                    createMessage(vacancy.toString()).setChatId(chatId)
            );
        }
        InlineKeyboardButton linkButton = tgmElementService
                .createCallbackButton(msg.getMessage("search.next", lang), "/nextsearchpage");
        InlineKeyboardMarkup keyboardMarkup = tgmElementService.createInlineKeyboardMarkup(
                tgmElementService.createInlineKeyboardRows(
                        tgmElementService.createInlineKeyboardRow(linkButton)
                )
        );
        vacancyMessages.add(createButtonMessage(keyboardMarkup).setChatId(chatId));

        return vacancyMessages;
    }

    @Override
    public List<SendMessage> createResumeMessages(
            String lang,
            Map<ResumeDTO, ResumeStatusDTO> resumeList,
            Long chatId
    ) {
        List<SendMessage> resumeMessages = new ArrayList<>();
        String browserText = msg.getMessage("browser.open", lang);
        String publishText = msg.getMessage("resume.publish", lang);

        for(Map.Entry<ResumeDTO, ResumeStatusDTO> resumeInfo : resumeList.entrySet()){
            ResumeDTO resume = resumeInfo.getKey();
            ResumeStatusDTO status = resumeInfo.getValue();

            InlineKeyboardButton browserButton = tgmElementService.createUrlButton(
                    browserText, resume.getAlternateUrl()
            );
            InlineKeyboardButton publishButton = tgmElementService.createCallbackButton(
                    publishText,  String.format("/publish %s", resume.getId())
            );

            InlineKeyboardMarkup buttonsRow = tgmElementService.createInlineKeyboardMarkup(
                    tgmElementService.createInlineKeyboardRows(
                            (status.getCanPublishOrUpdate())
                                    ? tgmElementService.createInlineKeyboardRow(publishButton, browserButton)
                                    : tgmElementService.createInlineKeyboardRow(browserButton)
                    )
            );
            resumeMessages.add(
                    createButtonMessage("\uD83D\uDCDD " + resume.toString(), buttonsRow).setChatId(chatId)
            );
        }

        return resumeMessages;
    }
}

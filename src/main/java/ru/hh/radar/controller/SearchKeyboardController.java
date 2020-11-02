package ru.hh.radar.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.dto.TypeDTO;
import ru.hh.radar.service.Utils;
import ru.hh.radar.service.hh.HhAreaService;
import ru.hh.radar.service.hh.HhSpecializationsService;
import ru.hh.radar.telegram.annotations.BotController;
import ru.hh.radar.telegram.annotations.BotRequestMapping;
import ru.hh.radar.telegram.service.*;

import java.util.List;

@BotController
@Api("Сервис отображения меню поиска вакансий")
@RequiredArgsConstructor
public class SearchKeyboardController {

    private final IncomingUpdateService incomingUpdateService;
    private final TelegramMessageService tgmMessageService;
    private final TelegramElementService elementService;
    private final InlineKeyboardService inlineKeyboardService;

    private final HhSpecializationsService hhSpecializationsService;
    private final HhAreaService hhAreaService;

    private final MessageService msg;

    @ApiOperation("Отображение меню поиска вакансий")
    @BotRequestMapping(value = "search.start", isLocale = true)
    public SendMessage startSearch(Update update) throws TelegramApiException {
        String lang = incomingUpdateService.getLanguageCode(update);
        return tgmMessageService.createMenuMessage(
                "️✏️",
                inlineKeyboardService.getMainSearchMenu(lang)
        ).setChatId(incomingUpdateService.getChatId(update));
    }

    @ApiOperation("Отображение меню 'Опыт работы'")
    @BotRequestMapping(value = "search.experience", isLocale = true)
    public SendMessage showExperienceMenu(Update update) throws TelegramApiException {
        String lang = incomingUpdateService.getLanguageCode(update);
        return tgmMessageService
                .createButtonMessage(inlineKeyboardService.getExperienceMenu(lang))
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @ApiOperation("Отображение меню 'Тип занятости'")
    @BotRequestMapping(value = "search.employment", isLocale = true)
    public SendMessage showEmploymentMenu(Update update) throws TelegramApiException {
        String lang = incomingUpdateService.getLanguageCode(update);
        return tgmMessageService
                .createButtonMessage(inlineKeyboardService.getEmploymentMenu(lang))
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @ApiOperation("Отображение меню 'График работы'")
    @BotRequestMapping(value = "search.schedule", isLocale = true)
    public SendMessage showScheduleMenu(Update update) throws TelegramApiException {
        String lang = incomingUpdateService.getLanguageCode(update);
        return tgmMessageService
                .createButtonMessage(inlineKeyboardService.getScheduleMenu(lang))
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @ApiOperation("Отображение меню 'Регион'")
    @BotRequestMapping(value = "search.area", isLocale = true)
    public SendMessage showAreaMenu(Update update) throws TelegramApiException {
        List<TypeDTO> areas = hhAreaService.getRussiaAreas();;
        InlineKeyboardMarkup inlineKeyboard =
                inlineKeyboardService.getPagingMenu(0, areas, "/search.area");

        return tgmMessageService
                .createButtonMessage(inlineKeyboard)
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @ApiOperation("Управление меню 'Регион': вперёд, назад")
    @BotRequestMapping(value = {"/search.area.next", "/search.area.back"})
    public EditMessageReplyMarkup showNextBackAreaMenu(Update update) throws TelegramApiException {
        String value =  Utils.getCommandValue(incomingUpdateService.getCommand(update));
        List<TypeDTO> areas = hhAreaService.getRussiaAreas();
        InlineKeyboardMarkup inlineKeyboard =
                inlineKeyboardService.getPagingMenu(Integer.decode(value), areas, "/search.area");

        return elementService.editMessageReplyMarkup(
                incomingUpdateService.getMessageId(update),
                inlineKeyboard
        ).setChatId(incomingUpdateService.getChatId(update));
    }

    @ApiOperation("Отображение меню 'Профобласть'")
    @BotRequestMapping(value = "search.specialization", isLocale = true)
    public SendMessage showSpecializationsMenu(Update update) throws TelegramApiException {
        List<TypeDTO> specializations = hhSpecializationsService.getSpecializations();
        InlineKeyboardMarkup inlineKeyboard =
                inlineKeyboardService.getPagingMenu(0, specializations, "/search.specialization");
        return tgmMessageService
                .createButtonMessage(inlineKeyboard)
                .setChatId(incomingUpdateService.getChatId(update));
    }

    @ApiOperation("Управление меню 'Профобласть': вперёд, назад")
    @BotRequestMapping(value = {"/search.specialization.next", "/search.specialization.back"})
    public EditMessageReplyMarkup showNextBackSpecializationMenu(Update update) throws TelegramApiException {
        String value =  Utils.getCommandValue(incomingUpdateService.getCommand(update));
        List<TypeDTO> specializations = hhSpecializationsService.getSpecializations();
        InlineKeyboardMarkup inlineKeyboard =
                inlineKeyboardService.getPagingMenu(Integer.decode(value), specializations, "/search.specialization");

        return elementService.editMessageReplyMarkup(
                incomingUpdateService.getMessageId(update),
                inlineKeyboard
        ).setChatId(incomingUpdateService.getChatId(update));
    }

    @ApiOperation("Отображение информации о способе задания должности")
    @BotRequestMapping(value = "search.text", isLocale = true)
    public SendMessage showPositionText(Update update) throws TelegramApiException {
        String lang = incomingUpdateService.getLanguageCode(update);
        return tgmMessageService.createMessage(
                msg.getMessage("message.search.parameters.position", lang)
        ).setChatId(incomingUpdateService.getChatId(update));
    }
}

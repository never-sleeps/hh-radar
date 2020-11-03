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
import ru.hh.radar.service.hh.HhDictionaryService;
import ru.hh.radar.telegram.annotations.BotController;
import ru.hh.radar.telegram.annotations.BotRequestMapping;
import ru.hh.radar.telegram.service.*;
import ru.hh.radar.utils.Utils;

import java.util.List;

@BotController
@Api("Сервис отображения меню поиска вакансий")
@RequiredArgsConstructor
public class SearchKeyboardController {

    private final IncomingUpdateService incomingUpdateService;
    private final TelegramMessageService tgmMessageService;
    private final TelegramElementService elementService;
    private final InlineKeyboardService inlineKeyboardService;

    private final HhDictionaryService hhDictionaryService;

    private final MessageService msg;

    @ApiOperation("Отображение меню поиска вакансий")
    @BotRequestMapping(value = "search.start", isLocale = true)
    public SendMessage startSearch(Update update) throws TelegramApiException {
        return tgmMessageService.createMenuMessage(
                msg.getMessage("message.search.main.mеnu", getLang(update)),
                inlineKeyboardService.getMainSearchMenu(getLang(update))
        ).setChatId(incomingUpdateService.getChatId(update));
    }

    @ApiOperation("Отображение меню 'Опыт работы'")
    @BotRequestMapping(value = "search.experience", isLocale = true)
    public SendMessage showExperienceMenu(Update update) throws TelegramApiException {
        return tgmMessageService
                .createButtonMessage(
                        msg.getMessage("message.search.experience", getLang(update)),
                        inlineKeyboardService.getExperienceMenu(getLang(update))
                ).setChatId(incomingUpdateService.getChatId(update));
    }

    @ApiOperation("Отображение меню 'Тип занятости'")
    @BotRequestMapping(value = "search.employment", isLocale = true)
    public SendMessage showEmploymentMenu(Update update) throws TelegramApiException {
        return tgmMessageService
                .createButtonMessage(
                        msg.getMessage("message.search.employment",getLang(update)),
                        inlineKeyboardService.getEmploymentMenu(getLang(update))
                ).setChatId(incomingUpdateService.getChatId(update));
    }

    @ApiOperation("Отображение меню 'График работы'")
    @BotRequestMapping(value = "search.schedule", isLocale = true)
    public SendMessage showScheduleMenu(Update update) throws TelegramApiException {
        return tgmMessageService
                .createButtonMessage(
                        msg.getMessage("message.search.schedule",getLang(update)),
                        inlineKeyboardService.getScheduleMenu(getLang(update))
                ).setChatId(incomingUpdateService.getChatId(update));
    }

    @ApiOperation("Отображение меню 'Регион'")
    @BotRequestMapping(value = "search.area", isLocale = true)
    public SendMessage showAreaMenu(Update update) throws TelegramApiException {
        List<TypeDTO> areas = hhDictionaryService.getRussiaAreas();;
        InlineKeyboardMarkup inlineKeyboard =
                inlineKeyboardService.getPagingMenu(0, areas, "/search.area");

        return tgmMessageService
                .createButtonMessage(
                        msg.getMessage("message.search.area",getLang(update)),
                        inlineKeyboard
                ).setChatId(incomingUpdateService.getChatId(update));
    }

    @ApiOperation("Управление меню 'Регион': вперёд, назад")
    @BotRequestMapping(value = {"/search.area.next", "/search.area.back"})
    public EditMessageReplyMarkup showNextBackAreaMenu(Update update) throws TelegramApiException {
        String value =  Utils.getCommandValue(incomingUpdateService.getCommand(update));
        List<TypeDTO> areas = hhDictionaryService.getRussiaAreas();
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
        List<TypeDTO> specializations = hhDictionaryService.getSpecializations();
        InlineKeyboardMarkup inlineKeyboard =
                inlineKeyboardService.getPagingMenu(0, specializations, "/search.specialization");
        return tgmMessageService
                .createButtonMessage(
                        msg.getMessage("message.search.specialization",getLang(update)),
                        inlineKeyboard
                ).setChatId(incomingUpdateService.getChatId(update));
    }

    @ApiOperation("Управление меню 'Профобласть': вперёд, назад")
    @BotRequestMapping(value = {"/search.specialization.next", "/search.specialization.back"})
    public EditMessageReplyMarkup showNextBackSpecializationMenu(Update update) throws TelegramApiException {
        String value =  Utils.getCommandValue(incomingUpdateService.getCommand(update));
        List<TypeDTO> specializations = hhDictionaryService.getSpecializations();
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
        return tgmMessageService.createMessage(
                msg.getMessage("message.search.parameters.position", getLang(update))
        ).setChatId(incomingUpdateService.getChatId(update));
    }

    private String getLang(Update update) throws TelegramApiException {
        return incomingUpdateService.getLanguageCode(update);
    }
}

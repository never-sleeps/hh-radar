package ru.hh.radar.controller;


import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.service.telegram.SearchService;
import ru.hh.radar.telegram.annotations.BotController;
import ru.hh.radar.telegram.annotations.BotRequestMapping;

@BotController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @BotRequestMapping("start.search")
    public SendMessage startSearch(Update update) throws TelegramApiException {
        return searchService.showSearchMenu(update);
    }

    @BotRequestMapping("search.experience")
    public SendMessage showExperienceMenu(Update update) throws TelegramApiException {
        return searchService.showExperienceMenu(update);
    }

    @BotRequestMapping(
            value = {
                    "/search.experience.noExperience", "/search.experience.between1And3",
                    "/search.experience.between3And6", "/search.experience.moreThan6"
            }
    )
    public SendMessage setExperienceMenu(Update update) throws TelegramApiException {
        return searchService.setExperienceSearchParameters(update);
    }

    @BotRequestMapping("search.employment")
    public SendMessage showEmploymentMenu(Update update) throws TelegramApiException {
        return searchService.showEmploymentMenu(update);
    }

    @BotRequestMapping("search.schedule")
    public SendMessage showScheduleMenu(Update update) throws TelegramApiException {
        return searchService.showScheduleMenu(update);
    }
}

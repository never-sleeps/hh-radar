package ru.hh.radar.service.telegram.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.hh.radar.dto.VacancyDTO;
import ru.hh.radar.model.entity.SearchParameters;
import ru.hh.radar.service.hh.HhVacancyService;
import ru.hh.radar.service.telegram.SearchService;
import ru.hh.radar.telegram.service.MessageService;
import ru.hh.radar.telegram.service.TelegramMessageService;

import java.util.List;

@SuppressWarnings("unchecked")
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final TelegramMessageService tgmMessageService;
    private final HhVacancyService hhVacancyService;

    private final MessageService msg;

    @Override
    public List<SendMessage> runSearch(SearchParameters parameters, String lang) {
        List<VacancyDTO> vacancies = hhVacancyService.getVacancies(parameters).getItems();
        return tgmMessageService.createVacancyMessages(
                msg.getMessage("browser.open", lang),
                vacancies
        );
    }
}
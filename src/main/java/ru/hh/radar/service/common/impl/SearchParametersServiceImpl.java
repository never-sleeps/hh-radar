package ru.hh.radar.service.common.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hh.radar.dto.TypeDTO;
import ru.hh.radar.model.SearchParametersType;
import ru.hh.radar.model.entity.Dictionary;
import ru.hh.radar.model.entity.SearchParameters;
import ru.hh.radar.repository.SearchParametersRepository;
import ru.hh.radar.service.common.DictionaryService;
import ru.hh.radar.service.common.SearchParametersService;
import ru.hh.radar.service.hh.HhDictionaryService;
import ru.hh.radar.telegram.service.MessageService;

@Service
@RequiredArgsConstructor
public class SearchParametersServiceImpl implements SearchParametersService {

    private final MessageService messageService;
    private final SearchParametersRepository repository;
    private final DictionaryService dictionaryService;
    private final HhDictionaryService hhDictionaryService;

    @Override
    public String toString(SearchParameters searchParameters, String lang) {
        if (searchParameters.isEmpty()) return messageService.getMessage("message.search.parameters.no", lang);

        Dictionary experience = dictionaryService.getDictionaryValue(SearchParametersType.EXPERIENCE, searchParameters.getExperience());
        Dictionary employment = dictionaryService.getDictionaryValue(SearchParametersType.EMPLOYMENT, searchParameters.getEmployment());
        Dictionary schedule = dictionaryService.getDictionaryValue(SearchParametersType.SCHEDULE, searchParameters.getSchedule());
        TypeDTO area = (searchParameters.getArea() != null) ? hhDictionaryService.getAreaById(searchParameters.getArea()) : null;

        return messageService.getMessage("message.search.parameters", lang) + "\n"
                + ((area != null) ? parameterToString("search.area", area.getName(), lang) : "")
                + parameterToString("search.specialization", searchParameters.getSpecialization(), lang)
                + parameterToString("search.text", searchParameters.getText(), lang)
                + ((experience != null) ? parameterToString("search.experience", experience.getTitle(), lang) : "")
                + ((employment != null) ? parameterToString("search.employment", employment.getTitle(), lang) : "")
                + ((schedule != null) ? parameterToString("search.schedule", schedule.getTitle(), lang) : "")
                ;
    }

    private String parameterToString(String name, String value, String lang) {
        return (value != null) ? String.format("%s: %s", messageService.getMessage(name, lang), value) + "\n" : "";
    }

    @Override
    public SearchParameters incrementSearchPage(SearchParameters parameters) {
        parameters.incrementPage();
        return repository.save(parameters);
    }

    @Override
    public SearchParameters resetSearchPage(SearchParameters parameters) {
        parameters.resetPage();
        return repository.save(parameters);
    }
}

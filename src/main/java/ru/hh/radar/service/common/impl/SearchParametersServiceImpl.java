package ru.hh.radar.service.common.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hh.radar.model.entity.SearchParameters;
import ru.hh.radar.repository.SearchParametersRepository;
import ru.hh.radar.service.common.SearchParametersService;
import ru.hh.radar.telegram.service.MessageService;

@RequiredArgsConstructor
@Service
public class SearchParametersServiceImpl implements SearchParametersService {

    private final MessageService messageService;
    private final SearchParametersRepository repository;

    @Override
    public String toString(SearchParameters searchParameters, String lang) {
        return messageService.getMessage("message.search.parameters", lang) + "\n"
                + parameterToString("search.area", searchParameters.getArea(), lang)
                + parameterToString("search.specialization", searchParameters.getSpecialization(), lang)
                + parameterToString("search.text", searchParameters.getText(), lang)
                + parameterToString("search.experience", searchParameters.getExperience(), lang)
                + parameterToString("search.employment", searchParameters.getEmployment(), lang)
                + parameterToString("search.schedule", searchParameters.getSchedule(), lang)
                ;
    }

    private String parameterToString(String name, String value, String lang) {
        return (value != null)
                ? String.format("%s: %s", messageService.getMessage(name, lang), value) + "\n"
                : "";
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

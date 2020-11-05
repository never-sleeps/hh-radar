package ru.hh.radar.service.common.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hh.radar.model.SearchParametersType;
import ru.hh.radar.model.entity.Dictionary;
import ru.hh.radar.repository.DictionaryRepository;
import ru.hh.radar.service.common.DictionaryService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DictionaryServiceImpl implements DictionaryService {

    private final DictionaryRepository repository;

    @Override
    public List<Dictionary> getDictionaryValuesByType(SearchParametersType type) {
        return repository.findByType(type.name().toLowerCase());
    }

    @Override
    public Dictionary getDictionaryValue(SearchParametersType type, String param) {
        return repository.findByTypeAndParam(type.name().toLowerCase(), param);
    }
}

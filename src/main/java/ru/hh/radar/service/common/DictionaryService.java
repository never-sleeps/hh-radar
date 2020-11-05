package ru.hh.radar.service.common;

import ru.hh.radar.model.SearchParametersType;
import ru.hh.radar.model.entity.Dictionary;

import java.util.List;

public interface DictionaryService {

    List<Dictionary> getDictionaryValuesByType(SearchParametersType type);

    Dictionary getDictionaryValue(SearchParametersType type, String param);
}

package ru.hh.radar.service.common;

import ru.hh.radar.model.entity.SearchParameters;

public interface SearchParametersService {
    String toString(SearchParameters searchParameters, String lang);

    SearchParameters incrementSearchPage(SearchParameters parameters);

    SearchParameters resetSearchPage(SearchParameters parameters);
}

package ru.hh.radar.service.common.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.hh.radar.model.SearchParametersType;
import ru.hh.radar.model.entity.Dictionary;
import ru.hh.radar.repository.DictionaryRepository;
import ru.hh.radar.service.common.DictionaryService;

import java.util.List;

import static org.mockito.BDDMockito.given;


@DisplayName("Сервис справочников")
@SpringBootTest(classes = DictionaryServiceImpl.class)
class DictionaryServiceImplTest {

    @MockBean
    private DictionaryRepository dictionaryRepository;

    @Autowired
    private DictionaryService dictionaryService;

    @Test
    @DisplayName("должен возвращать объект по типу")
    void shouldGetDictionaryValuesByType() {
        // given
        String type = SearchParametersType.AREA.name().toLowerCase();
        Dictionary dictionary = Dictionary.builder()
                .param("param")
                .title("title")
                .type(type)
                .build();
        given(dictionaryRepository.findByType(type)).willReturn(List.of(dictionary));

        // when
        List<Dictionary> foundDictionaries = dictionaryService.getDictionaryValuesByType(SearchParametersType.AREA);

        // then
        Assertions.assertEquals(List.of(dictionary).size(), foundDictionaries.size());
        Assertions.assertEquals(List.of(dictionary), foundDictionaries);
    }

    @Test
    @DisplayName("должен возвращать объект по значение")
    void shouldGetDictionaryValue() {
        // given
        String type = SearchParametersType.AREA.name().toLowerCase();
        String param = "param";
        Dictionary dictionary = Dictionary.builder()
                .param(param)
                .title("title")
                .type(type)
                .build();
        given(dictionaryRepository.findByTypeAndParam(type, param)).willReturn(dictionary);

        // when
        Dictionary foundDictionary = dictionaryService.getDictionaryValue(SearchParametersType.AREA, param);

        // then
        Assertions.assertEquals(dictionary, foundDictionary);
    }
}
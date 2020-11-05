package ru.hh.radar.service.common.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.hh.radar.model.entity.SearchParameters;
import ru.hh.radar.repository.SearchParametersRepository;
import ru.hh.radar.service.common.DictionaryService;
import ru.hh.radar.service.common.SearchParametersService;
import ru.hh.radar.service.hh.HhDictionaryService;
import ru.hh.radar.telegram.service.MessageService;

import static org.mockito.BDDMockito.given;


@DisplayName("Сервис параметров поиска вакансий")
@SpringBootTest(classes = SearchParametersServiceImpl.class)
class SearchParametersServiceImplTest {

    @MockBean
    private MessageService messageService;
    @MockBean
    private SearchParametersRepository repository;
    @MockBean
    private DictionaryService dictionaryService;
    @MockBean
    private HhDictionaryService hhDictionaryService;
    @Autowired
    private SearchParametersService searchParametersService;

    @Test
    @DisplayName("должен увеличивать значение страницы")
    void shouldIncrementSearchPage() {
        // given
        SearchParameters searchParametersBefore = SearchParameters.builder()
                .page(5L)
                .build();
        SearchParameters searchParametersAfter = SearchParameters.builder()
                .page(6L)
                .build();
        given(repository.save(searchParametersAfter)).willReturn(searchParametersAfter);
        // when
        SearchParameters updatedSearchParameters = searchParametersService.incrementSearchPage(searchParametersBefore);

        // then
        Assertions.assertEquals(searchParametersAfter.getPage(), updatedSearchParameters.getPage());
    }

    @Test
    @DisplayName("должен сбрасывать значение страницы")
    void shouldResetSearchPage() {
        // given
        long pageBefore = 5L;
        long pageAfter = 1L;
        SearchParameters searchParametersBefore = SearchParameters.builder()
                .page(pageBefore)
                .build();
        SearchParameters searchParametersAfter = SearchParameters.builder()
                .page(pageAfter)
                .build();
        given(repository.save(searchParametersAfter)).willReturn(searchParametersAfter);

        // when
        SearchParameters updatedSearchParameters = searchParametersService.resetSearchPage(searchParametersBefore);

        // then
        Assertions.assertEquals(searchParametersAfter.getPage(), updatedSearchParameters.getPage());
    }
}
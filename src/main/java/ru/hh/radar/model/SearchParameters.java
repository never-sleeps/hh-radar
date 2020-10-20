package ru.hh.radar.model;

import lombok.Data;
import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static ru.hh.radar.model.SearchParameters.SearchParam.AREA;

/**
 * ПАРАМЕТРЫ ПОИСКА
 *
 *  Некоторые параметры принимают множественные значения: key=value&key=value.
 *
 *  1) text — текстовое поле. Переданное значение ищется в полях вакансии,
 *  указанных в параметре search_field (По умолчанию, используются все поля).
 *
 *  2) experience — опыт работы. Справочник с возможными значениями: experience в /dictionaries.
 *
 *  3) employment — тип занятости. Справочник с возможными значениями: employment в /dictionaries.
 *  Возможно указание нескольких значений.
 *
 *  4) schedule — график работы. Справочник с возможными значениями: schedule в /dictionaries.
 *  Возможно указание нескольких значений.
 *
 *  5) area — регион. Справочник с возможными значениями: /areas. Возможно указание нескольких значений.
 *
 *  6) specialization — профобласть или специализация. Справочник с возможными значениями: /specializations.
 *  Возможно указание нескольких значений.
 *
 *  7) employer_id — идентификатор компании.
 *  Возможно указание нескольких значений.
 *
 *  8) salary — размер заработной платы (RUR).
 *
 *  9) period — количество дней, в пределах которых нужно найти вакансии. Максимальное значение: 30.
 *
 *  10) order_by — сортировка списка вакансий. Справочник с возможными значениями: vacancy_search_order в /dictionaries.
 *  Если выбрана сортировка по удалённости от гео-точки distance, необходимо также задать её координаты sort_point_lat,sort_point_lng.
 *
 *  11) describe_arguments — возвращать ли описание использованных параметров поиска, по умолчанию: false.
 *
 *  12) per_page, page — параметры пагинации. Параметр per_page ограничен значением в 100.
 *
 *  13) no_magic – Если значение true – отключить автоматическое преобразование вакансий. По-умолчанию – false. При включённом автоматическом преобразовании, будет предпринята попытка изменить текстовый запрос пользователя на набор параметров. Например, запрос text=москва бухгалтер 100500 будет преобразован в text=бухгалтер&only_with_salary=true&area=1&salary=100500.
 */
@Data
public class SearchParameters {

    private Map<SearchParam, String> searchParameter;

    public SearchParameters() {
        searchParameter = new HashMap<>();
        searchParameter.put(AREA, "1");
    }

    public void put(SearchParam searchParam, String value) {
        searchParameter.put(searchParam, value);
    }

    public Map<SearchParam, String> get() {
        return Collections.unmodifiableMap(searchParameter);
    }

    @Getter
    public enum SearchParam {
        TEXT("search.text"),
        EXPERIENCE("search.experience"),
        EMPLOYMENT("search.employment"),
        SCHEDULE("search.schedule"),
        AREA("search.area"),
        SPECIALIZATION("search.specialization"),
        EMPLOYER_ID("search.employer_id"),
        SALARY("search.salary");

        String param;
        SearchParam(String param) {
          this.param = param;
        }
    }
}
package ru.hh.radar.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;
import ru.hh.radar.model.SearchParametersType;
import ru.hh.radar.model.entity.SearchParameters;
import ru.hh.radar.model.entity.User;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class WebRequestUtils {

    public static HttpHeaders getAuthorizationHttpHeader(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + Utils.decode(user.getClientAccessToken().getAccessToken()));
        return headers;
    }

    public static UriComponentsBuilder applySearchParameters(
            UriComponentsBuilder uriComponentsBuilder,
            Map<SearchParametersType, String> searchParameters
    ) {
        for(Map.Entry<SearchParametersType, String> param : searchParameters.entrySet()) {
            if(param.getValue() == null) continue;
            uriComponentsBuilder = uriComponentsBuilder
                    .queryParam(
                            param.getKey().name().toLowerCase(),
                            param.getValue()
                    );
        }
        return uriComponentsBuilder;
    }

    public static Map<SearchParametersType, String> toParametersMap(SearchParameters parameters) {
        Map<SearchParametersType, String> map = new HashMap<>();
        map.put(SearchParametersType.SPECIALIZATION, parameters.getSpecialization());
        map.put(SearchParametersType.EXPERIENCE, parameters.getExperience());
        map.put(SearchParametersType.EMPLOYMENT, parameters.getEmployment());
        map.put(SearchParametersType.SCHEDULE, parameters.getSchedule());
        map.putAll(toShortParametersMap(parameters));
        return map;
    }

    public static Map<SearchParametersType, String> toShortParametersMap(SearchParameters parameters) {
        Map<SearchParametersType, String> map = new HashMap<>();
        map.put(SearchParametersType.AREA, parameters.getArea());
        map.put(SearchParametersType.ORDER_BY, parameters.getOrderBy());
        map.put(SearchParametersType.PAGE, String.valueOf(parameters.getPage()));
        map.put(SearchParametersType.PER_PAGE, String.valueOf(parameters.getPerPage()));
        map.put(SearchParametersType.ORDER, parameters.getOrder());
        map.put(SearchParametersType.TEXT, parameters.getText());
        return map;
    }
}

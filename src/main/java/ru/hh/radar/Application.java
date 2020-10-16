package ru.hh.radar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.ApiContextInitializer;
import ru.hh.radar.config.LocaleConfig;
import ru.hh.radar.dto.SearchParameters;
import ru.hh.radar.dto.VacanciesSearchResultsDTO;
import ru.hh.radar.dto.VacancyDTO;
import ru.hh.radar.service.hh.HhVacancyService;

import java.util.List;

/**
 * Created by iakonyakina
 * iakonyakina@gmail.com
 */
@SpringBootApplication
@EnableConfigurationProperties(LocaleConfig.class)
public class Application {

	public static void main(String[] args) {
		ApiContextInitializer.init();
		SpringApplication.run(Application.class, args);

//		ApplicationContext ctx = SpringApplication.run(Application.class, args);
//		HhVacancyService service = ctx.getBean(HhVacancyService.class);
//		SearchParameters searchParameters = new SearchParameters();
//		searchParameters.put(SearchParameters.SearchParam.EMPLOYMENT, "part");
//		searchParameters.put(SearchParameters.SearchParam.EXPERIENCE, "noExperience");
//		VacanciesSearchResultsDTO vacancyDTO = service.getVacancies(searchParameters);
//		System.out.println();
	}

}

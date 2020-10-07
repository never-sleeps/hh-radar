package ru.hh.radar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import ru.hh.radar.telegram.TelegramBot;

/**
 * Created by iakonyakina
 * iakonyakina@gmail.com
 */
@SpringBootApplication
public class Application {

//	public static void main(String[] args) {
//		ApiContextInitializer.init();
////		SpringApplication.run(Application.class, args);
//
//		ApplicationContext ctx = SpringApplication.run(Application.class, args);
//		HhVacancyService service = ctx.getBean(HhVacancyService.class);
//
////		VacancyDTO vacancyDTO = service.getVacancy(4095490L);
////		System.out.println(vacancyDTO);
//
//
//
////		List<SearchParameter> searchParameters =
////				Collections.singletonList(new SearchParameter("experience", "noExperience"));
////		VacanciesSearchResultsDTO vacancies = service.getVacancies(searchParameters);
////		System.out.println(vacancies);
//	}

	public static void main(String[] args) {
		ApiContextInitializer.init();
		SpringApplication.run(Application.class, args);
	}

}

package ru.hh.radar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.ApiContextInitializer;
import ru.hh.radar.dto.VacancyDTO;
import ru.hh.radar.service.hh.HhVacancyService;

/**
 * Created by iakonyakina
 * iakonyakina@gmail.com
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ApiContextInitializer.init();
//		SpringApplication.run(Application.class, args);

		ApplicationContext ctx = SpringApplication.run(Application.class, args);
		HhVacancyService service = ctx.getBean(HhVacancyService.class);

//		VacancyDTO vacancyDTO = service.getVacancy(4095490L);
//		System.out.println(vacancyDTO);



//		List<SearchParameter> searchParameters =
//				Collections.singletonList(new SearchParameter("experience", "noExperience"));
//		VacanciesSearchResultsDTO vacancies = service.getVacancies(searchParameters);
//		System.out.println(vacancies);
	}

}

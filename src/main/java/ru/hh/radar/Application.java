package ru.hh.radar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.hh.radar.dto.SearchParameter;
import ru.hh.radar.dto.VacanciesSearchResultsDTO;
import ru.hh.radar.dto.VacancyDTO;
import ru.hh.radar.service.HhVacancyService;

import java.util.Collections;
import java.util.List;

/**
 * Created by iakonyakina
 * iakonyakina@gmail.com
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
//		SpringApplication.run(Application.class, args);

		ApplicationContext ctx = SpringApplication.run(Application.class, args);
		HhVacancyService service = ctx.getBean(HhVacancyService.class);

		VacancyDTO vacancyDTO = service.getVacancy(4095490L);
		System.out.println(vacancyDTO);

		service.getVacancy(4095490L);
		service.getVacancy(4095490L);



//		List<SearchParameter> searchParameters =
//				Collections.singletonList(new SearchParameter("experience", "noExperience"));
//		VacanciesSearchResultsDTO vacancies = service.getVacancies(searchParameters);
//		System.out.println(vacancies);
	}

}

package ru.hh.radar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.telegram.telegrambots.ApiContextInitializer;
import ru.hh.radar.config.LocaleConfig;

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
//
//		VacancyDTO vacancyDTO = service.getVacancy(4095490L);
//		System.out.println(vacancyDTO);
	}

}

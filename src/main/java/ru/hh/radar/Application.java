package ru.hh.radar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.ApiContextInitializer;
import ru.hh.radar.config.LocaleConfig;

/**
 * Created by iakonyakina
 * iakonyakina@gmail.com
 */
@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(LocaleConfig.class)
public class Application {

	public static void main(String[] args) {
		ApiContextInitializer.init();
		SpringApplication.run(Application.class, args);
	}

}

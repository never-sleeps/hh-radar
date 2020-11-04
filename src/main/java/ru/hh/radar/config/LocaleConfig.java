package ru.hh.radar.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "application")
@Setter
public class LocaleConfig {
    private Map<String, String> languages;

    public List<String> getLocales() {
        List<String> list = new ArrayList<>();
        for(Map.Entry<String, String> language : languages.entrySet()){
            list.add(language.getKey());
        }
        return list;
    }

    @Bean
    MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/bundle");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
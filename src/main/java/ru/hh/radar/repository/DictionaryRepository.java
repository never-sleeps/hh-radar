package ru.hh.radar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hh.radar.model.entity.Dictionary;

import java.util.List;

public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {

    List<Dictionary> findByType(String type);

    Dictionary findByTypeAndParam(String type, String param);
}

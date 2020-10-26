package ru.hh.radar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hh.radar.model.entity.SearchParameters;

public interface SearchParametersRepository extends JpaRepository<SearchParameters, Long> {
}

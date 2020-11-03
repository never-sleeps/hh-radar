package ru.hh.radar.service.hh;

import ru.hh.radar.dto.TypeDTO;

import java.util.List;

public interface HhDictionaryService {

    List<TypeDTO> getRussiaAreas();

    List<TypeDTO> getSpecializations();
}

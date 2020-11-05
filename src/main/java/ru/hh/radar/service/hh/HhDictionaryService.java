package ru.hh.radar.service.hh;

import ru.hh.radar.dto.TypeDTO;

import java.util.List;

public interface HhDictionaryService {

    List<TypeDTO> getRussiaAreas();

    TypeDTO getAreaById(String areaId);

    List<TypeDTO> getSpecializations();
}

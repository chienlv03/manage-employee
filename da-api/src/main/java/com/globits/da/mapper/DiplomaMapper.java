package com.globits.da.mapper;

import com.globits.da.domain.Diploma;
import com.globits.da.dto.DiplomaDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiplomaMapper {
    public DiplomaDto toDto(Diploma diploma) {
        if (diploma == null) {
            return null;
        }

        DiplomaDto diplomaDto = new DiplomaDto();
        diplomaDto.setId(diploma.getId());
        diplomaDto.setName(diploma.getName());
        diplomaDto.setType(diploma.getType());
        diplomaDto.setStartDate(diploma.getStartDate());
        diplomaDto.setEndDate(diploma.getEndDate());
        diplomaDto.setEmployeeId(diploma.getEmployee().getId());
        diplomaDto.setProvinceId(diploma.getProvince().getId());

        return diplomaDto;
    }

    public Diploma toEntity(DiplomaDto diplomaDto) {
        if (diplomaDto == null) {
            return null;
        }

        Diploma diploma = new Diploma();
        diploma.setId(diplomaDto.getId());
        diploma.setName(diplomaDto.getName());
        diploma.setType(diplomaDto.getType());
        diploma.setStartDate(diplomaDto.getStartDate());
        diploma.setEndDate(diplomaDto.getEndDate());

        return diploma;
    }

    public List<DiplomaDto> toDtoList(List<Diploma> diplomas) {
        if (diplomas == null || diplomas.isEmpty()) {
            return new ArrayList<>();
        }
        return diplomas.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}

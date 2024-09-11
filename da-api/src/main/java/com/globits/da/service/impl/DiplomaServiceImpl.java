package com.globits.da.service.impl;
import com.globits.da.dto.response.ApiResponse;
import com.globits.da.FindEntity;
import com.globits.da.domain.Diploma;
import com.globits.da.domain.Employee;
import com.globits.da.domain.Province;
import com.globits.da.dto.DiplomaDto;
import com.globits.da.mapper.DiplomaMapper;
import com.globits.da.repository.DiplomaRepository;
import com.globits.da.service.DiplomaService;
import com.globits.da.utils.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class DiplomaServiceImpl implements DiplomaService {

    private final DiplomaRepository diplomaRepository;
    private final FindEntity findEntity;
    private final DiplomaMapper diplomaMapper;
    private final Validation validation;

    @Override
    public ApiResponse<DiplomaDto> addDiploma(DiplomaDto diplomaDto) {

        Diploma diploma = processDiploma(diplomaDto);
        DiplomaDto dto = diplomaMapper.toDto(diplomaRepository.save(diploma));
        return new ApiResponse<>(dto);
    }

    @Override
    public ApiResponse<DiplomaDto> updateDiploma(DiplomaDto diplomaDto) {
        Diploma diploma = processDiploma(diplomaDto);

        // Set additional fields for update
        diploma.setName(diplomaDto.getName());
        diploma.setType(diplomaDto.getType());
        diploma.setStartDate(diplomaDto.getStartDate());
        diploma.setEndDate(diplomaDto.getEndDate());

        DiplomaDto dto = diplomaMapper.toDto(diplomaRepository.save(diploma));
        return new ApiResponse<>(dto);
    }

    @Override
    public ApiResponse<List<DiplomaDto>> findDiplomaByEmployeeId(Integer employeeId) {
        List<Diploma> diplomas = diplomaRepository.findByEmployeeId(employeeId);
        return new ApiResponse<>(diplomaMapper.toDtoList(diplomas));
    }

    @Override
    public ApiResponse<List<DiplomaDto>> findDiplomaByProvinceId(Integer provinceId) {
        List<Diploma> diplomas = diplomaRepository.findByProvinceId(provinceId);
        return new ApiResponse<>(diplomaMapper.toDtoList(diplomas));
    }

    @Override
    public ApiResponse<List<DiplomaDto>> getAllDiplomas() {
        return new ApiResponse<>(diplomaRepository.findAll().stream()
                .map(diplomaMapper::toDto)
                .collect(Collectors.toList()));
    }

    @Override
    public void deleteDiploma(Integer id) {
        Diploma diploma = findEntity.findDiploma(id);
        diplomaRepository.delete(diploma);
    }

    private Diploma processDiploma(DiplomaDto diplomaDto) {
        validation.validateStartDateAndEndDate(diplomaDto.getStartDate(), diplomaDto.getEndDate());
        Employee employee = findEntity.findEmployee(diplomaDto.getEmployeeId());
        Province province = findEntity.findProvince(diplomaDto.getProvinceId());

        validation.validateDiplomaUniqueness(employee, diplomaDto.getType(), province.getId());
        validation.validateDiplomaIsEffect(employee, diplomaDto.getType(), LocalDate.now());

        Diploma diploma = diplomaMapper.toEntity(diplomaDto);
        diploma.setEmployee(employee);
        diploma.setProvince(province);

        return diploma;
    }
}

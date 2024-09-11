package com.globits.da.service;

import com.globits.da.dto.response.ApiResponse;
import com.globits.da.dto.DiplomaDto;

import java.util.List;

public interface DiplomaService {
    ApiResponse<DiplomaDto> addDiploma(DiplomaDto diplomaDto);

    ApiResponse<DiplomaDto> updateDiploma(DiplomaDto diplomaDto);

    ApiResponse<List<DiplomaDto>> findDiplomaByEmployeeId(Integer employeeId);

    ApiResponse<List<DiplomaDto>> getAllDiplomas();

    void deleteDiploma(Integer id);

    ApiResponse<List<DiplomaDto>> findDiplomaByProvinceId(Integer provinceId);
}

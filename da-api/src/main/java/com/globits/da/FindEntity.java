package com.globits.da;

import com.globits.da.domain.*;
import com.globits.da.exception.ResourceNotFoundException;
import com.globits.da.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindEntity {
    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final CommuneRepository communeRepository;
    private final EmployeeRepository employeeRepository;
    private final DiplomaRepository diplomaRepository;

    public Province findProvince(Integer id) {
        return provinceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.PROVINCE_NOT_EXITED));

    }

    public District findDistrict(Integer id) {
        return districtRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.DISTRICT_NOT_EXITED));
    }

    public Commune findCommune(Integer id) {
        return communeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.COMMUNE_NOT_EXITED));
    }

    public Employee findEmployee(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.EMPLOYEE_NOT_EXITED));
    }

    public Diploma findDiploma(Integer id) {
        return diplomaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.DIPLOMA_NOT_FOUND));
    }


}

package com.globits.da.utils;

import com.globits.da.MessageConstants;
import com.globits.da.domain.Commune;
import com.globits.da.domain.District;
import com.globits.da.domain.Employee;
import com.globits.da.domain.Province;
import com.globits.da.dto.request.EmployeeImportExcelRequest;
import com.globits.da.exception.BadRequestException;
import com.globits.da.exception.ResourceNotFoundException;
import com.globits.da.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class Validation {

    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final CommuneRepository communeRepository;
    private final DiplomaRepository diplomaRepository;
    private final EmployeeRepository employeeRepository;

    public void validateProvince(Integer provinceId) {
        if (provinceId == null || !provinceRepository.existsById(provinceId)) {
            throw new ResourceNotFoundException(MessageConstants.PROVINCE_NOT_EXITED);
        }
    }

    public void validateDistrict(Integer districtId, Integer provinceId) {
        if (districtId == null || !districtRepository.existsById(districtId)) {
            throw new ResourceNotFoundException(MessageConstants.DISTRICT_NOT_EXITED);
        }

        District district = districtRepository.findById(districtId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.DISTRICT_NOT_EXITED));

        if (!district.getProvince().getId().equals(provinceId)) {
            throw new BadRequestException(MessageConstants.DISTRICT_NOT_BELONG_TO_PROVINCE);
        }
    }

    public void validateCommune(Integer communeId, Integer districtId) {
        if (communeId == null || !communeRepository.existsById(communeId)) {
            throw new ResourceNotFoundException(MessageConstants.COMMUNE_NOT_EXITED);
        }

        Commune commune = communeRepository.findById(communeId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.COMMUNE_NOT_EXITED));

        if (!commune.getDistrict().getId().equals(districtId)) {
            throw new BadRequestException(MessageConstants.COMMUNE_NOT_BELONG_TO_DISTRICT);
        }
    }

    public void validateDiplomaUniqueness(Employee employee, String diplomaType, Integer provinceId) {
        Province province = provinceRepository.findById(provinceId).orElseThrow(() ->
                new ResourceNotFoundException(MessageConstants.PROVINCE_NOT_EXITED));

        boolean exists = diplomaRepository.existsByEmployeeAndTypeAndProvinceAndEndDateAfter(
                employee, diplomaType, province, LocalDate.now());

        if (exists) {
            throw new BadRequestException(MessageConstants.EMPLOYEE_ALREADY_HAS_DIPLOMA);
        }
    }


    public void validateDiplomaIsEffect(Employee employee, String type, LocalDate date) {
        int count = diplomaRepository.countByEmployeeAndTypeAndEndDateAfter(employee, type, date);
        if (count >= 3) {
            throw new BadRequestException(MessageConstants.EMPLOYEE_CANNOT_HAVE_MORE_THAN_3_CERTIFICATES);
        }
    }

    public void validateProvinceName(String name) {
        if (provinceRepository.existsByName(name)) {
            throw new BadRequestException(MessageConstants.PROVINCE_NAME_EXITED);
        }
    }

    public void validateDistrictName(String name) {
        if (districtRepository.existsByName(name)) {
            throw new BadRequestException(MessageConstants.DISTRICT_NAME_EXITED);
        }
    }

    public void validateCodeUnique(String code) {
        if (employeeRepository.existsByCode(code)) {
            throw new BadRequestException(MessageConstants.CODE_UNIQUE);
        }
    }

    public String validateEmployeeData(EmployeeImportExcelRequest employee) {
        StringBuilder errorMessage = new StringBuilder();

        // Kiểm tra các trường bắt buộc
        if (employee.getName() == null || employee.getName().isEmpty()) {
            errorMessage.append("Employee name is required. ");
        }
        if (employee.getEmail() == null || employee.getEmail().isEmpty()) {
            errorMessage.append("Employee email is required. ");
        }
        if (employee.getPhone() == null || employee.getPhone().isEmpty()) {
            errorMessage.append("Employee phone is required. ");
        }

        // Kiểm tra mối quan hệ Province-District-Commune
        String validationError = validateLocation(
                employee.getProvinceName(), employee.getDistrictName(), employee.getCommuneName());
        if (!validationError.isEmpty()) {
            errorMessage.append(validationError);
        }

        return errorMessage.toString();
    }


    private String validateLocation(String provinceName, String districtName, String communeName) {
        Province province = provinceRepository.findByName(provinceName);
        if (province == null) {
            return MessageConstants.PROVINCE_NOT_EXITED;
        }

        District district = districtRepository.findByName(districtName);
        if (district == null) {
            return MessageConstants.DISTRICT_NOT_EXITED;
        }
        if (!district.getProvince().equals(province)) {
            return MessageConstants.DISTRICT_NOT_BELONG_TO_PROVINCE;
        }

        Commune commune = communeRepository.findByName(communeName);
        if (commune == null) {
            return MessageConstants.COMMUNE_NOT_EXITED;
        }
        if (!commune.getDistrict().equals(district)) {
            return MessageConstants.COMMUNE_NOT_BELONG_TO_DISTRICT;
        }

        return "";
    }

    public void validateStartDateAndEndDate(LocalDate startDate, LocalDate endDate) {
        if (!endDate.isAfter(LocalDate.now()) || !startDate.isBefore(LocalDate.now())) {
            throw new BadRequestException(MessageConstants.VALIDATE_DATE);
        }
    }
}

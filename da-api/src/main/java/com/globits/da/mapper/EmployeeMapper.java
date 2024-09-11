package com.globits.da.mapper;

import com.globits.da.domain.Commune;
import com.globits.da.domain.District;
import com.globits.da.domain.Employee;
import com.globits.da.domain.Province;
import com.globits.da.dto.DiplomaDto;
import com.globits.da.dto.request.EmployeeImportExcelRequest;
import com.globits.da.dto.request.EmployeeRequest;
import com.globits.da.dto.response.CommuneResponse;
import com.globits.da.dto.response.DistrictResponse;
import com.globits.da.dto.response.EmployeeResponse;
import com.globits.da.dto.response.ProvinceResponse;
import com.globits.da.repository.CommuneRepository;
import com.globits.da.repository.DistrictRepository;
import com.globits.da.repository.ProvinceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmployeeMapper {

    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final CommuneRepository communeRepository;
    private final DiplomaMapper diplomaMapper;

    public EmployeeResponse toResponse(Employee employee) {
        if (employee == null) {
            return null;
        }
        ProvinceResponse provinceResponse = new ProvinceResponse(employee.getProvince().getId(), employee.getProvince().getName());
        DistrictResponse districtResponse = new DistrictResponse(employee.getDistrict().getId(), employee.getDistrict().getName(), employee.getDistrict().getProvince().getId());
        CommuneResponse communeResponse = new CommuneResponse(employee.getCommune().getId(), employee.getCommune().getName(), employee.getCommune().getDistrict().getId());

        List<DiplomaDto> diplomaDtoList = diplomaMapper.toDtoList(employee.getDiplomas());

        EmployeeResponse response = new EmployeeResponse();
        response.setId(employee.getId());
        response.setCode(employee.getCode());
        response.setName(employee.getName());
        response.setEmail(employee.getEmail());
        response.setPhone(employee.getPhone());
        response.setAge(employee.getAge());
        response.setProvince(provinceResponse);
        response.setDistrict(districtResponse);
        response.setCommune(communeResponse);
        response.setDiplomas(diplomaDtoList);
        return response;
    }

    public Employee toEntity(EmployeeRequest request) {
        if (request == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setId(request.getId());
        employee.setCode(request.getCode());
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setAge(request.getAge());
        return employee;
    }

    public Employee toEntity(EmployeeImportExcelRequest request) {
        if (request == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setCode(request.getCode());
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setAge(request.getAge());

        Province province = provinceRepository.findByName(request.getProvinceName());
        District district = districtRepository.findByName(request.getDistrictName());
        Commune commune = communeRepository.findByName(request.getCommuneName());

        employee.setProvince(province);
        employee.setDistrict(district);
        employee.setCommune(commune);

        return employee;
    }

    public List<Employee> toEmployeeList(List<EmployeeImportExcelRequest> employeeRequests) {
        return employeeRequests.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}

package com.globits.da.service.impl;

import com.globits.da.MessageConstants;
import com.globits.da.dto.request.EmployeeImportExcelRequest;
import com.globits.da.dto.request.EmployeeRequest;
import com.globits.da.dto.response.ApiResponse;
import com.globits.da.domain.Employee;
import com.globits.da.dto.response.EmployeeResponse;
import com.globits.da.dto.response.MessageResponse;
import com.globits.da.exception.BadRequestException;
import com.globits.da.exception.ResourceNotFoundException;
import com.globits.da.mapper.EmployeeMapper;
import com.globits.da.repository.CommuneRepository;
import com.globits.da.repository.DistrictRepository;
import com.globits.da.repository.EmployeeRepository;
import com.globits.da.repository.ProvinceRepository;
import com.globits.da.service.EmployeeService;
import com.globits.da.utils.ImportExportExcelUtil;
import com.globits.da.utils.Validation;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final CommuneRepository communeRepository;
    private final EmployeeMapper employeeMapper;
    private final ImportExportExcelUtil importExportExcelUtil;
    private final Validation validation;

    @Override
    public ApiResponse<List<EmployeeResponse>> getAllEmployees() {
        return new ApiResponse<>(employeeRepository.findAll().stream()
                .map(employeeMapper::toResponse)
                .collect(Collectors.toList()));
    }

    @Override
    public ApiResponse<EmployeeResponse> getEmployeeById(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.EMPLOYEE_NOT_EXITED));
        return new ApiResponse<>(employeeMapper.toResponse(employee));
    }

    @Override
    public ApiResponse<EmployeeResponse> addEmployee(EmployeeRequest request) {
        validation.validateProvince(request.getProvinceId());
        validation.validateDistrict(request.getDistrictId(), request.getProvinceId());
        validation.validateCommune(request.getCommuneId(), request.getDistrictId());

        validation.validateCodeUnique(request.getCode());

        // Chuyển đổi từ EmployeeResponse sang Employee
        Employee employee = employeeMapper.toEntity(request);
        employee.setProvince(provinceRepository.findById(request.getProvinceId()).get());
        employee.setDistrict(districtRepository.findById(request.getDistrictId()).get());
        employee.setCommune(communeRepository.findById(request.getCommuneId()).get());

        // Lưu lại Employee
        Employee savedEmployee = employeeRepository.save(employee);

        // Trả về kết quả dưới dạng ApiResponse
        EmployeeResponse savedEmployeeRes = employeeMapper.toResponse(savedEmployee);
        return new ApiResponse<>(savedEmployeeRes);
    }

    @Override
    public ApiResponse<EmployeeResponse> updateEmployee(EmployeeRequest request) {
        // Kiểm tra xem employee có tồn tại hay không
        Employee existingEmployee = employeeRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.EMPLOYEE_NOT_EXITED));

        validation.validateProvince(request.getProvinceId());
        validation.validateDistrict(request.getDistrictId(), request.getProvinceId());
        validation.validateCommune(request.getCommuneId(), request.getDistrictId());

        // Cập nhật thông tin Employee
        existingEmployee.setCode(request.getCode());
        existingEmployee.setName(request.getName());
        existingEmployee.setEmail(request.getEmail());
        existingEmployee.setPhone(request.getPhone());
        existingEmployee.setAge(request.getAge());
        existingEmployee.setProvince(provinceRepository.findById(request.getProvinceId()).get());
        existingEmployee.setDistrict(districtRepository.findById(request.getDistrictId()).get());
        existingEmployee.setCommune(communeRepository.findById(request.getCommuneId()).get());

        // Lưu lại Employee
        employeeRepository.save(existingEmployee);

        // Trả về kết quả dưới dạng ApiResponse
        EmployeeResponse updatedEmployeeRes = employeeMapper.toResponse(existingEmployee);
        return new ApiResponse<>(updatedEmployeeRes);
    }

    @Override
    public void deleteEmployee(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.EMPLOYEE_NOT_EXITED));
        employeeRepository.deleteById(employee.getId());
        new MessageResponse(MessageConstants.DELETE_SUCCESS);
    }

    @Override
    public void deleteAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            throw new ResourceNotFoundException(MessageConstants.EMPLOYEE_NOT_EXITED);
        }
        employeeRepository.deleteAll();
    }

    @Override
    public void exportExcel(HttpServletResponse response) throws IOException {
        List<EmployeeResponse> employees = employeeRepository.findAll().stream()
                .map(employeeMapper::toResponse)
                .collect(Collectors.toList());
        try (Workbook workbook = importExportExcelUtil.exportEmployeesExcelFile(employees);
             ServletOutputStream outputStream = response.getOutputStream()) {
             response.setContentType("application/octet-stream");
             response.setHeader("Content-Disposition", "attachment;filename=Employees.xlsx");
             workbook.write(outputStream);
        } catch (IOException e) {
            throw new BadRequestException(MessageConstants.FAILED_EXPORT_EXCEL);
        }
    }

    @Override
    public void importExcel(MultipartFile file) {
        List<EmployeeImportExcelRequest> employees = importExportExcelUtil.readEmployeeFromExcel(file);
        List<String> errors = new ArrayList<>();

        for (int i = 0; i < employees.size(); i++) {
            EmployeeImportExcelRequest employee = employees.get(i);
            String error = validation.validateEmployeeData(employee);

            if (!error.isEmpty()) {
                errors.add("Row " + (i + 2) + ": " + error);
            }
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join("\n", errors));
        }

        // If no error, map to entity and save to DB
        List<Employee> employeeEntities = employeeMapper.toEmployeeList(employees);
        employeeRepository.saveAll(employeeEntities);
    }
}

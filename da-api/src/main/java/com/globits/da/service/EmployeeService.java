package com.globits.da.service;

import com.globits.da.dto.request.EmployeeRequest;
import com.globits.da.dto.response.ApiResponse;
import com.globits.da.dto.response.EmployeeResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

public interface EmployeeService {
    ApiResponse<List<EmployeeResponse>> getAllEmployees();

    ApiResponse<EmployeeResponse> getEmployeeById(Integer id);

    ApiResponse<EmployeeResponse> addEmployee(EmployeeRequest request);

    ApiResponse<EmployeeResponse> updateEmployee(@Valid EmployeeRequest request);

    void deleteEmployee(Integer id);

    void deleteAllEmployees();

    void exportExcel(HttpServletResponse response) throws IOException;

    void importExcel(MultipartFile file);
}
